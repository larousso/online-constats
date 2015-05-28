

var Conducteur = React.createClass({
        getInitialState() {
            return {
                conducteur : {
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
        onCaseChange(caseName) {
            var _this = this;
            return function(value) {
                var conducteur = {circonstances: {}};
                conducteur.circonstances[caseName] = value;
                console.log("case change", conducteur);
                _this.setState({conducteur: conducteur});
            };
        },
        validateModele(conducteur){
            var errors = [];
            if(!conducteur.nom) {
                errors.push({field: "nom", label:"Le nom est obligatoire"});
            }
            if(!conducteur.prenom) {
                errors.push({field: "prenom", label:"Le prenom est obligatoire"});
                //errors.prenom = "Le prenom est obligatoire";
            }
            if(!conducteur.immatriculation || !conducteur.immatriculation.immatriculation) {
                errors.push({field: "immatriculation", label:"La plaque d'immatriculation est obligatoire"});
                //errors.immatriculation = "La plaque d'immatriculation est obligatoire";
            }
            return errors;
        },
        validate() {
            var conducteur = {
                nom: React.findDOMNode(this.refs.nom).value,
                prenom: React.findDOMNode(this.refs.prenom).value,
                immatriculation: {
                    immatriculation: React.findDOMNode(this.refs.immatriculation).value
                },
                circonstances : this.state.conducteur.circonstances
            };
            var errors = this.validateModele(conducteur);
            if(errors && errors.length>0) {
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
        render() {

            var prev;
            if(this.props.prev) {
                prev = (<Link className="btn btn-link btn-nav pull-left" to={this.props.prev.link}>
                        <span className="icon icon-left-nav"></span>
                        {this.props.prev.title}
                    </Link>);
            }
            var next;
            if(this.props.next) {
                next = (<Link className="btn btn-link btn-nav pull-right" to={this.props.next.link} onClick={this.validate}>
                    {this.props.next.title}
                    <span className="icon icon-right-nav"></span>
                </Link>);
            }
            var errors;
            if(this.state.errors) {
                errors = this.state.errors.map( (err, i) => (<div key={i}><span>{err.label}</span><br/></div>));
            }

            return (
                <div>
                    <header className="bar bar-nav">
                        {prev}
                        {next}
                        <h1 className="title">{this.props.title}</h1>
                    </header>
                    <div className="content">
                        {errors}
                        <form className="input-group" onSubmit={this.handleSubmit}>
                            <h2>Conducteur</h2>
                            <div className="input-row">
                                <label>Nom</label>
                                <input type="text" placeholder="Nom" ref="nom"/>
                            </div>
                            <div className="input-row form-group has-error">
                                <label>Prénom</label>
                                <input type="text" className="form-control" placeholder="Prénom" ref="prenom"/>
                            </div>
                            <div className="input-row">
                                <label>Immatriculation</label>
                                <input type="text" placeholder="Immatriculation" ref="immatriculation"/>
                            </div>
                            <br/>
                            <h2>Circonstances</h2>
                            <ul className="table-view">
                                <li className="table-view-cell">
                                    Quittait son stationnement
                                    <Toggle onToggle={this.onCaseChange("case2")} active={this.state.case2}/>
                                </li>
                                <li className="table-view-cell">
                                    Sortait d’un parking, d’un lieu privé, d’un chemin de terre
                                    <Toggle onToggle={this.onCaseChange("case4")} active={this.state.case4}/>
                                </li>
                                <li className="table-view-cell">
                                    Heurtait l’arrière en roulant dans le même sens et sur une même file
                                    <Toggle onToggle={this.onCaseChange("case8")} active={this.state.case8}/>
                                </li>
                                <li className="table-view-cell">
                                    Changeait de file
                                    <Toggle onToggle={this.onCaseChange("case10")} active={this.state.case10}/>
                                </li>
                                <li className="table-view-cell">
                                    Reculait
                                    <Toggle onToggle={this.onCaseChange("case14")} active={this.state.case14}/>
                                </li>
                                <li className="table-view-cell">
                                    Empiétait sur une voie réservée à la circulation en sens inverse
                                    <Toggle onToggle={this.onCaseChange("case15")} active={this.state.case15}/>
                                </li>
                                <li className="table-view-cell">
                                    N’avait pas observé le signal de sécurité
                                    <Toggle onToggle={this.onCaseChange("case16")} active={this.state.case16}/>
                                </li>
                            </ul>
                            <Link to={this.props.next.link} className="btn btn-block btn btn-primary" onClick={this.validate}>Continuer</Link>
                        </form>
                    </div>
                </div>
            );
        }
    }
);