import React from 'react'
import ReactDOM from 'react-dom'

class CurrentVisitors extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			visitors: []
		}
		this.fetchDataFromServer = this.fetchDataFromServer.bind(this);
	}

	componentDidMount() {
		$('#currentVisitorsContainer').on('update', (event) => {
			console.log("observed, time to update current visitors")
			this.fetchDataFromServer();
			console.log("observed done!!!")
		})
	}

	componentWillMount () {
		this.fetchDataFromServer()
		//setInterval(this.fetchDataFromServer, 2000);
	}

	componentWillUnmount () {
		$('currentVisitorsContainer').off('update');
	}

	fetchDataFromServer () {
		console.log("fetchDataFromServer")
		$.get('/api/visit/current').done( (data) =>
			{
				/*console.log('polling success');
				console.log('data', data);*/
				this.setState({visitors: data.data})
			});
	}

	render () {
		return (
			<div>
				<h2>Current Visitors</h2>
				<Visitors visitors={this.state.visitors} />
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
			<div className="test">
			<table className="table table-striped">
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
							<tr key={index}>
								<td>{visitor.person_fullname}</td>
								<td>{visitor.check_in}</td>
							</tr>
						);
					})}
				</tbody>
			</table>
			</div>
		);
	}
}

ReactDOM.render(
	<CurrentVisitors />, document.getElementById('currentVisitorsContainer'))