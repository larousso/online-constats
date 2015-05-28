"use strict";

var ConstatService = {
    save: function save(model) {

        $.ajax({
            type: "POST",
            url: "http://localhost:9000/constats",
            crossDomain: true,
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(model),
            success: function success() {
                console.log("Success");
            }
        });
        console.log(model);
    }
};