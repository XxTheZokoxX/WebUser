// window.addEventListener("load", function() {});
window.onload = function () {
    function encrypt(valor) {
        let splitStr = valor.split("");
        let reverseArrray = splitStr.reverse();
        let joinArray = reverseArrray.join("");
        let valorFinal = "Token-Propio-2323" + joinArray;
        md5(valorFinal);
        let hash = md5.create();
        hash.update(valor);
        return hash.hex();
    }
    let beforeSend = function (event) {
        let form = event.target;
        event.preventDefault();
        let password = document.getElementById("password");
        if (password.value.length > 2) {
            let passwdEncrypt = encrypt(password.value);
            let inputHiddenPsswd = document.createElement("input");
            inputHiddenPsswd.setAttribute("type", "hidden");
            inputHiddenPsswd.setAttribute("name", "password_encript");
            inputHiddenPsswd.setAttribute("value", passwdEncrypt);
            form.appendChild(inputHiddenPsswd);
            form.removeChild(
                    form.password);
            form.submit();
        } else {
            alert("Password too short.");
        }
    }
    // Pasamos la función como parámetro en la Callback
    
    document.forms[0].addEventListener("submit", beforeSend);
};
