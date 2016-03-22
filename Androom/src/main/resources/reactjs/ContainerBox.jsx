import React from 'react'
import ReactDOM from 'react-dom'
import ReactCSSTransitionGroup from 'react-addons-css-transition-group'

import RegisterVisitor from './component/RegisterVisitor.jsx'
import DeregisterVisitor from './component/DeregisterVisitor.jsx'

class ContainerBox extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			mounted: false
		};
		this.clickHandler = this.clickHandler.bind(this);
	}

	clickHandler () {
		console.log("clickHandler: called")
		if (this.state.mounted)
			this.setState({mounted: false})
		else
			this.setState({mounted: true})
	}

	render () {
		var customComponent;
		if (this.state.mounted) {
			var CustomComponent = this.props.customComponent;
			customComponent = <CustomComponent clickHandler={this.clickHandler} />
		} else {
			customComponent = null;
		}

		return (
			<div>
				<button
					className="btn btn-default"
					onClick={this.clickHandler}>
					{this.props.buttonName}
				</button>
				<ReactCSSTransitionGroup
					transitionName="containerBox"
					transitionEnterTimeout={500}
					transitionLeaveTimeout={300}>
					{customComponent}
				</ReactCSSTransitionGroup>
			</div>
		);
	}
}

ReactDOM.render(
	<ContainerBox customComponent={RegisterVisitor} buttonName="Register visit" />,
		document.getElementById('registerVisitor'));

ReactDOM.render(
	<ContainerBox customComponent={DeregisterVisitor} buttonName="Deregister visit" />,
		document.getElementById('deregisterVisitor'));