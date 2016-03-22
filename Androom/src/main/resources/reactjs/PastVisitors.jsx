import React from 'react'
import ReactDOM from 'react-dom'

class PastVisitors extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			visitors: []
		}
		this.fetchDataFromServer = this.fetchDataFromServer.bind(this);
	}

	componentDidMount() {
		$('#pastVisitorContainer').on('update', (event) => {
			console.log("observed, time to update past visitors")
			this.fetchDataFromServer();
		})
	}

	componentWillMount () {
		this.fetchDataFromServer()
		// setInterval(this.fetchDataFromServer, 2000);
	}

	componentWillUnmount () {
		$('#pastVisitorContainer').off('update');
	}

	fetchDataFromServer () {

		$.get('/api/visit/past').done( (data) =>
			{
				/*console.log('polling success');
				console.log('data', data);*/
				this.setState({visitors: data.data})
			});
	}

	render () {
		return (
			<div>
				<h2>Past Visitors</h2>
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
			<div className="table-responsive">
			<table className="table table-striped">
				<thead>
					<tr>
						<th>Name</th>
						<th>Check in Time</th>
						<th>Check out Time</th>
					</tr>
				</thead>
				<tbody>

				{this.props.visitors.map(
					(visitor, index) => {
						return (
							<tr key={index}>
								<td>{visitor.person_fullname}</td>
								<td>{visitor.check_in}</td>
								<td>{visitor.check_out}</td>
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
	<PastVisitors />, document.getElementById('pastVisitorContainer'))