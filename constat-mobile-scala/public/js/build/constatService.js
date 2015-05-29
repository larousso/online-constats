"use strict";

var ConstatService = {
    save: function save(model) {
        console.log(model);
        return $.ajax({
            type: "POST",
            url: "http://localhost:9000/constats",
            crossDomain: true,
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(model)
        });
    }
};