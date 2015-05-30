"use strict";

var Router = window.ReactRouter;
var Route = Router.Route;
var RouteHandler = Router.RouteHandler;
var Navigation = Router.Navigation;
var Link = Router.Link;

var ConstatModel = {};

var ConducteurA = React.createClass({
    displayName: "ConducteurA",

    registerConducteur: function registerConducteur(conducteur) {
        ConstatModel.conducteurA = conducteur;
    },
    render: function render() {
        var next = {
            title: "Conducteur B",
            link: "conducteurB"
        };
        return React.createElement(Conducteur, { title: "Conducteur A", next: next, registerConducteur: this.registerConducteur });
    }
});

var ConducteurB = React.createClass({
    displayName: "ConducteurB",

    registerConducteur: function registerConducteur(conducteur) {
        ConstatModel.conducteurB = conducteur;
    },
    render: function render() {
        var prev = {
            title: "Conducteur A",
            link: "conducteurA"
        };
        var next = {
            title: "Notes diverses",
            link: "description"
        };
        return React.createElement(Conducteur, { title: "Conducteur B", prev: prev, next: next, registerConducteur: this.registerConducteur });
    }
});

var Description = React.createClass({
    displayName: "Description",

    mixins: [Navigation], // Use the mixin
    getInitialState: function getInitialState() {
        return { error: false };
    },
    validate: function validate() {
        var _this = this;
        ConstatModel.description = React.findDOMNode(this.refs.description).value;
        ConstatService.save(ConstatModel).done(function (rep) {
            _this.setState({
                error: false
            });
            _this.transitionTo("/");
        }).fail(function (err) {
            _this.setState({
                error: true
            });
        });
    },
    render: function render() {
        var err;
        if (this.state.error) {
            err = React.createElement(
                "span",
                null,
                "Une erreur est survenue"
            );
        }
        return React.createElement(
            "div",
            null,
            React.createElement(
                "header",
                { className: "bar bar-nav" },
                React.createElement(
                    Link,
                    { className: "btn btn-link btn-nav pull-left", to: "conducteurB" },
                    React.createElement("span", { className: "icon icon-left-nav" }),
                    "Conducteur B"
                ),
                React.createElement(
                    "h1",
                    { className: "title" },
                    "Description"
                )
            ),
            React.createElement(
                "div",
                { className: "content" },
                err,
                React.createElement(
                    "form",
                    null,
                    React.createElement(
                        "label",
                        null,
                        "Notes diverses"
                    ),
                    React.createElement("textarea", { rows: "5", ref: "description" })
                ),
                React.createElement(
                    "button",
                    { className: "btn btn-block btn btn-primary", onClick: this.validate },
                    "Valider le constat"
                )
            )
        );
    }
});

var App = React.createClass({
    displayName: "App",

    render: function render() {
        return React.createElement(
            "div",
            null,
            React.createElement(
                "header",
                { className: "bar bar-nav" },
                React.createElement(
                    "h1",
                    null,
                    "Constat en ligne"
                )
            ),
            React.createElement(
                "div",
                { className: "content" },
                React.createElement(
                    Link,
                    { to: "conducteurA", className: "btn btn-block btn btn-primary" },
                    "Démarrer la saisie"
                )
            ),
            React.createElement(RouteHandler, null)
        );
    }
});

// declare our routes and their hierarchy
var routes = React.createElement(
    Route,
    { handler: App },
    React.createElement(Route, { name: "conducteurA", path: "conducteurA", handler: ConducteurA }),
    React.createElement(Route, { name: "conducteurB", path: "conducteurB", handler: ConducteurB }),
    React.createElement(Route, { name: "description", path: "description", handler: Description })
);

Router.run(routes, Router.HashLocation, function (Root) {
    React.render(React.createElement(Root, null), document.body);
});