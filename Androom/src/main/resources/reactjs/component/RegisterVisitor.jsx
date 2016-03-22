import React from 'react'
import ReactDOM from 'react-dom'
import ReactCSSTransitionGroup from 'react-addons-css-transition-group'

var DEFAULT_VALUE = "";
var FIRST_NAME = "first name";
var LAST_NAME = "last name";

export default class RegisterVisitor extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			defaultFirstName: DEFAULT_VALUE + FIRST_NAME,
			defaultLastName: DEFAULT_VALUE + LAST_NAME,
			clickHandler: this.props.clickHandler
		}

		this.registerVisitor = this.registerVisitor.bind(this);
		this.handleInputChange = this.handleInputChange.bind(this);
		this.getPersonId = this.getPersonId.bind(this);
	}

	handleInputChange(event) {
		var propertyName = event.target.name;
		var propertyValue = event.target.value;
		var newState = {};
		newState[propertyName] = propertyValue
		this.setState(newState, this.getPersonId);
		console.log(this.state)
	}

	getPersonId() {
		var firstName = this.state.firstName;
		var lastName = this.state.lastName;
		if (firstName && lastName)
			$.get(
				'/api/person',
				{
					firstName: firstName,
					lastName: lastName
				})
			.done((data) => {
				console.log(data);
				if (data.status === "NOT_FOUND") {
					this.setState({personId: 0})
				} else {
					this.setState({personId: data.data.id})
				}
			})
	}

	registerVisitor () {
		console.log("register visitor", this.state.fullName);

		var tzoffset = (new Date()).getTimezoneOffset() * 60000;
		var localISOTime = (new Date(Date.now() - tzoffset)).toISOString().slice(0,-1);

		var fullName = this.state.firstName + " " + this.state.lastName;

		var personId = this.state.personId;
		console.log("personId:", personId);

		var postData = JSON.stringify({
			'person_id': personId,
			'check_in': localISOTime,
			'person_fullname': fullName
		})

		$.ajax({
			url: '/api/visit',
			type: 'POST',
			data: postData,
			contentType: 'application/json'
		})
		.done((result) => {

				console.log("finished registering");
				$('#currentVisitorsContainer').trigger('update');
				if (result.status === 'OK') {
					this.props.clickHandler();
					var n = noty(
					{
						text        : result.status,
						type        : 'success',
						timeout     : 2000,
						closeWith   : ['click'],
						layout      : 'topCenter',
						theme       : 'defaultTheme',
						maxVisible  : 10
					});
				} else {
					var n = noty(
					{
						text        : result.message,
						type        : 'warning',
						timeout     : 2000,
						closeWith   : ['click'],
						layout      : 'topCenter',
						theme       : 'defaultTheme',
						maxVisible  : 10
					});
				}
			}
		);
	}

	render () {
		console.log('register visitor rendering')
		return (
			<div>
				<div id="modalBackground"></div>
				<div className="container innerBox">
					<div className="row">
						<h1 className="header col-md-6">Register your visit</h1>
					</div>
					<div className="row form-group">
						<input
							className="form-control col-md-2"
							type="firstName"
							name="firstName"
							placeholder={this.state.defaultFirstName}
							onChange={this.handleInputChange}
							 />
					</div>
					<div className="row form-group">
						<input
							className="form-control col-md-2"
							type="lastName"
							name="lastName"
							placeholder={this.state.defaultLastName}
							onChange={this.handleInputChange}
							 />
					</div>
					<div className="btn-group row">
						<button
							className="btn btn-default btn-sm"
							onClick={this.registerVisitor}>Register</button>
						<button
							className="btn btn-default btn-sm"
							onClick={this.props.clickHandler}>Close</button>
					</div>
				</div>
			</div>
		);
	}
}