window.addEventListener("load", () => {
    document.getElementById("btnSend").onclick = function () {
        let email = document.getElementById("email").value;
        let password = document.getElementById("password").value;
        let name = document.getElementById("name").value;
        let age = document.getElementById("age").value;

        let objUser = {
            "email": email,
            password: password
        }
        objUser.name = name;
        objUser["age"] = age;

        let jsonTextUser = JSON.stringify(objUser);


        let ajaxObj = new XMLHttpRequest();

        ajaxObj.open("POST", "api/users");
               
        ajaxObj.onload = function () {
            // do something to response
            console.log(this.responseText);
            alert(this.responseText);
            let objRespUser = JSON.parse(this.responseText);
            document.getElementById("id").value = objRespUser.id;
        };
        /*
         * Envío del JSON mediante un parámetro de formulario
        ajaxObj.setRequestHeader('Content-Type', 
                'application/x-www-form-urlencoded; charset=ISO-8859-1');
        ajaxObj.send("json=" + jsonTextUser);
        */
        // Envío del JSON como cuerpo de la petición HTTP
        ajaxObj.setRequestHeader('Content-Type', 
                "application/json;charset=UTF-8");
        ajaxObj.send(jsonTextUser);      
    };
});
  