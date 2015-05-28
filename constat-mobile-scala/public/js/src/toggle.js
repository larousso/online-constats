function cx() {
    var className = '';
    for (var i = 0; i < arguments.length; i++) {
        if (arguments[i]) className = className+' '+arguments[i]
    }
    return className;
}

var Toggle = React.createClass({
    componentDidMount() {
        this.refs.toggle.getDOMNode().addEventListener('toggle', this.handleToggle)
        var fingerBlast = new FingerBlast(this.refs.toggle.getDOMNode());
    },
    componentWillUnmount() {
        this.refs.toggle.getDOMNode().removeEventListener('toggle', this.handleToggle)
    },
    handleToggle(e) {
        var inverse = !this.props.active;
        if (e.detail.isActive == inverse) {
            this.props.onToggle(inverse);
        }
    },
    render() {
        var extraClasses = [];
        if (this.props.active) extraClasses.push('active');
        var className = cx.apply(null, [this.props.className, "toggle"].concat(extraClasses));

        return (
            <div {...this.props} className={className} ref="toggle">
                <div className="toggle-handle" />
            </div>
        )
    }
});