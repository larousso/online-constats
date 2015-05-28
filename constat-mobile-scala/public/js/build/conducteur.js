"use strict";

var Conducteur = React.createClass({
    displayName: "Conducteur",

    getInitialState: function getInitialState() {
        return {
            conducteur: {
                nom: "",
                prenom: "",
                immatriculation: {
                    immatriculation: ""
                },
                circonstances: {
                    case2: false,
                    case4: false,
                    case8: false,
                    case10: false,
                    case14: false,
                    case15: false,
                    case16: false
                }
            }
        };
    },
    onCaseChange: function onCaseChange(caseName) {
        var _this = this;
        return function (value) {
            var conducteur = { circonstances: {} };
            conducteur.circonstances[caseName] = value;
            console.log("case change", conducteur);
            _this.setState({ conducteur: conducteur });
        };
    },
    validateModele: function validateModele(conducteur) {
        var errors = [];
        if (!conducteur.nom) {
            errors.push({ field: "nom", label: "Le nom est obligatoire" });
        }
        if (!conducteur.prenom) {
            errors.push({ field: "prenom", label: "Le prenom est obligatoire" });
            //errors.prenom = "Le prenom est obligatoire";
        }
        if (!conducteur.immatriculation || !conducteur.immatriculation.immatriculation) {
            errors.push({ field: "immatriculation", label: "La plaque d'immatriculation est obligatoire" });
            //errors.immatriculation = "La plaque d'immatriculation est obligatoire";
        }
        return errors;
    },
    validate: function validate() {
        var conducteur = {
            nom: React.findDOMNode(this.refs.nom).value,
            prenom: React.findDOMNode(this.refs.prenom).value,
            immatriculation: {
                immatriculation: React.findDOMNode(this.refs.immatriculation).value
            },
            circonstances: this.state.conducteur.circonstances
        };
        var errors = this.validateModele(conducteur);
        if (errors && errors.length > 0) {
            this.setState({
                conducteur: conducteur,
                errors: errors
            });
            return false;
        } else {
            this.props.registerConducteur(conducteur);
            console.log("OKKKK", conducteur);
            return true;
        }
    },
    render: function render() {

        var prev;
        if (this.props.prev) {
            prev = React.createElement(
                Link,
                { className: "btn btn-link btn-nav pull-left", to: this.props.prev.link },
                React.createElement("span", { className: "icon icon-left-nav" }),
                this.props.prev.title
            );
        }
        var next;
        if (this.props.next) {
            next = React.createElement(
                Link,
                { className: "btn btn-link btn-nav pull-right", to: this.props.next.link, onClick: this.validate },
                this.props.next.title,
                React.createElement("span", { className: "icon icon-right-nav" })
            );
        }
        var errors;
        if (this.state.errors) {
            errors = this.state.errors.map(function (err, i) {
                return React.createElement(
                    "div",
                    { key: i },
                    React.createElement(
                        "span",
                        null,
                        err.label
                    ),
                    React.createElement("br", null)
                );
            });
        }

        return React.createElement(
            "div",
            null,
            React.createElement(
                "header",
                { className: "bar bar-nav" },
                prev,
                next,
                React.createElement(
                    "h1",
                    { className: "title" },
                    this.props.title
                )
            ),
            React.createElement(
                "div",
                { className: "content" },
                errors,
                React.createElement(
                    "form",
                    { className: "input-group", onSubmit: this.handleSubmit },
                    React.createElement(
                        "h2",
                        null,
                        "Conducteur"
                    ),
                    React.createElement(
                        "div",
                        { className: "input-row" },
                        React.createElement(
                            "label",
                            null,
                            "Nom"
                        ),
                        React.createElement("input", { type: "text", placeholder: "Nom", ref: "nom" })
                    ),
                    React.createElement(
                        "div",
                        { className: "input-row form-group has-error" },
                        React.createElement(
                            "label",
                            null,
                            "Prénom"
                        ),
                        React.createElement("input", { type: "text", className: "form-control", placeholder: "Prénom", ref: "prenom" })
                    ),
                    React.createElement(
                        "div",
                        { className: "input-row" },
                        React.createElement(
                            "label",
                            null,
                            "Immatriculation"
                        ),
                        React.createElement("input", { type: "text", placeholder: "Immatriculation", ref: "immatriculation" })
                    ),
                    React.createElement("br", null),
                    React.createElement(
                        "h2",
                        null,
                        "Circonstances"
                    ),
                    React.createElement(
                        "ul",
                        { className: "table-view" },
                        React.createElement(
                            "li",
                            { className: "table-view-cell" },
                            "Quittait son stationnement",
                            React.createElement(Toggle, { onToggle: this.onCaseChange("case2"), active: this.state.case2 })
                        ),
                        React.createElement(
                            "li",
                            { className: "table-view-cell" },
                            "Sortait d’un parking, d’un lieu privé, d’un chemin de terre",
                            React.createElement(Toggle, { onToggle: this.onCaseChange("case4"), active: this.state.case4 })
                        ),
                        React.createElement(
                            "li",
                            { className: "table-view-cell" },
                            "Heurtait l’arrière en roulant dans le même sens et sur une même file",
                            React.createElement(Toggle, { onToggle: this.onCaseChange("case8"), active: this.state.case8 })
                        ),
                        React.createElement(
                            "li",
                            { className: "table-view-cell" },
                            "Changeait de file",
                            React.createElement(Toggle, { onToggle: this.onCaseChange("case10"), active: this.state.case10 })
                        ),
                        React.createElement(
                            "li",
                            { className: "table-view-cell" },
                            "Reculait",
                            React.createElement(Toggle, { onToggle: this.onCaseChange("case14"), active: this.state.case14 })
                        ),
                        React.createElement(
                            "li",
                            { className: "table-view-cell" },
                            "Empiétait sur une voie réservée à la circulation en sens inverse",
                            React.createElement(Toggle, { onToggle: this.onCaseChange("case15"), active: this.state.case15 })
                        ),
                        React.createElement(
                            "li",
                            { className: "table-view-cell" },
                            "N’avait pas observé le signal de sécurité",
                            React.createElement(Toggle, { onToggle: this.onCaseChange("case16"), active: this.state.case16 })
                        )
                    ),
                    React.createElement(
                        Link,
                        { to: this.props.next.link, className: "btn btn-block btn btn-primary", onClick: this.validate },
                        "Continuer"
                    )
                )
            )
        );
    }
});