
var Router = window.ReactRouter;
var Route = Router.Route;
var RouteHandler = Router.RouteHandler;
var Link = Router.Link;

var ConstatModel = {
};

var ConducteurA = React.createClass({
    registerConducteur(conducteur) {
        ConstatModel.conducteurA = conducteur
    },
    render() {
        var next = {
            title: "Conducteur B",
            link: "conducteurB"
        };
        return <Conducteur title="Conducteur A" next={next} registerConducteur={this.registerConducteur}/>;
    }
});

var ConducteurB = React.createClass({
    registerConducteur(conducteur) {
        ConstatModel.conducteurB = conducteur
    },
    render() {
        var prev = {
            title: "Conducteur A",
            link: "conducteurA"
        };
        var next = {
            title: "Notes diverses",
            link: "description"
        };
        return <Conducteur title="Conducteur B" prev={prev} next={next} registerConducteur={this.registerConducteur}/>;
    }
});

var Description = React.createClass({
    validate() {
        ConstatModel.description = React.findDOMNode(this.refs.description).value;
        ConstatService.save(ConstatModel);
        return true;
    },
    render() {
        return (
            <div>
                <header className="bar bar-nav">
                    <Link className="btn btn-link btn-nav pull-left" to="conducteurB">
                        <span className="icon icon-left-nav"></span>
                        Conducteur B
                    </Link>
                    <h1 className="title">Description</h1>
                </header>
                <div className="content">
                    <form>
                        <label>Notes diverses</label>
                        <textarea rows="5" ref="description"></textarea>
                    </form>
                    <Link to="/" className="btn btn-block btn btn-primary" onClick={this.validate}>Valider le constat</Link>
                </div>
            </div>
        );
    }
});



var App = React.createClass({
    render () {
        return (
            <div>
                <header className="bar bar-nav">
                    <h1>Constat en ligne</h1>
                </header>
                <div className="content">
                    <Link to="conducteurA" className="btn btn-block btn btn-primary">Démarrer la saisie</Link>
                </div>
                <RouteHandler/>
            </div>
        )
    }
});


// declare our routes and their hierarchy
var routes = (
    <Route handler={App}>
        <Route name="conducteurA" path="conducteurA" handler={ConducteurA}/>
        <Route name="conducteurB" path="conducteurB" handler={ConducteurB}/>
        <Route name="description" path="description" handler={Description}/>
    </Route>
);

Router.run(routes, Router.HashLocation, (Root) => {
    React.render(<Root/>, document.body);
});
