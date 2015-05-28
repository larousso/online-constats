'use strict';

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

function cx() {
    var className = '';
    for (var i = 0; i < arguments.length; i++) {
        if (arguments[i]) className = className + ' ' + arguments[i];
    }
    return className;
}

var Toggle = React.createClass({
    displayName: 'Toggle',

    componentDidMount: function componentDidMount() {
        this.refs.toggle.getDOMNode().addEventListener('toggle', this.handleToggle);
        var fingerBlast = new FingerBlast(this.refs.toggle.getDOMNode());
    },
    componentWillUnmount: function componentWillUnmount() {
        this.refs.toggle.getDOMNode().removeEventListener('toggle', this.handleToggle);
    },
    handleToggle: function handleToggle(e) {
        var inverse = !this.props.active;
        if (e.detail.isActive == inverse) {
            this.props.onToggle(inverse);
        }
    },
    render: function render() {
        var extraClasses = [];
        if (this.props.active) extraClasses.push('active');
        var className = cx.apply(null, [this.props.className, 'toggle'].concat(extraClasses));

        return React.createElement(
            'div',
            _extends({}, this.props, { className: className, ref: 'toggle' }),
            React.createElement('div', { className: 'toggle-handle' })
        );
    }
});