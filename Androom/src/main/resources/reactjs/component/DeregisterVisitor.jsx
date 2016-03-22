import React from 'react'
import ReactDOM from 'react-dom'



export default class DeregisterVisitor extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			visitors: [],
			personIds: [] //container for visitors to be checked out
		}
		this.fetchDataFromServer = this.fetchDataFromServer.bind(this);
		this.onChangeHandler = this.onChangeHandler.bind(this);
		this.deregisterVisitor = this.deregisterVisitor.bind(this);
	}

	componentWillMount () {
		this.fetchDataFromServer()
	}

	componentWillUnmount () {
		console.log("unmount")
	}

	fetchDataFromServer () {
		$.get('/api/visit/current')
		.done( (data) => {
			/*console.log('polling success');
			console.log('data', data);*/
			this.setState({visitors: data.data})
		});
	}

	deregisterVisitor (personId) {
		var arrayData = {person_ids: this.state.personIds};
		console.log("DeregisterVisitor=", arrayData);

		$.ajax({
			url			: 	'/api/visit',
			type 		:   'PUT',
			data 		: 	JSON.stringify(arrayData),
			contentType	: 	'application/json',
		})
		.done( (data => {
			console.log('done:', data.result.status)
			if (data.result.status === 'OK') {
				$('#currentVisitorsContainer').trigger('update');
				$('#pastVisitorContainer').trigger('update');
				var n = noty(
				{
					text        : data.result.message,
					type        : 'success',
					timeout     : 2000,
					closeWith   : ['click'],
					layout      : 'topCenter',
					theme       : 'defaultTheme',
					maxVisible  : 10
				});
				this.setState({personIds: []});
			} else {
				var n = noty(
				{
					text        : data.result.message,
					type        : 'warning',
					timeout     : 2000,
					closeWith   : ['click'],
					layout      : 'topCenter',
					theme       : 'defaultTheme',
					maxVisible  : 10
				});
			}
			this.props.clickHandler();
		}))

	}

	onChangeHandler (event) {
		var elem = event.currentTarget;
		var personId = elem.getAttribute('value');
		if (elem.getAttribute('style') === 'background-color: #f9f9f9') {
			elem.setAttribute('style', 'background-color: #fff');
			var index = this.state.personIds.indexOf(personId)
			if (index > -1) {
				this.state.personIds.splice(index, 1);
			}
		}
		else {
			elem.setAttribute('style', 'background-color: #f9f9f9');
			if (this.state.personIds.indexOf(personId) === -1) {
				this.state.personIds.push(personId);
			}
		}
		console.log(this.state.personIds);
		console.log(personId);
		this.setState({personId: personId})
	}

	render () {
		return (
			<div>
				<div id="modalBackground"></div>
				<div className="container innerBox">
					<h2>Deregister your visit</h2>
					<Visitors
						visitors={this.state.visitors}
						onChangeHandler={this.onChangeHandler} />
					<div className="btn-group">
						<button
							className="btn btn-default btn-sm"
							onClick={this.deregisterVisitor}>Deregister
						</button>
						<button
							className="btn btn-default btn-sm"
							onClick={this.props.clickHandler}>Close
						</button>
					</div>
				</div>
			</div>
		);
	}
}

class Visitors extends React.Component {
	constructor(props) {
		super(props);
	}

	render () {
		return (
			<table
				className="table"
				id="currentVisitors">
				<thead>
					<tr>
						<th>Name</th>
						<th>Check in Time</th>
					</tr>
				</thead>
				<tbody>

				{this.props.visitors.map(
					(visitor, index) => {
						return (
							<tr
								key={index}
								value={visitor.person_id}
								onClick={this.props.onChangeHandler}
							>
								<td>{visitor.person_fullname}</td>
								<td>{visitor.check_in}</td>
							</tr>
						);
					})}
				</tbody>
			</table>
		);
	}
}