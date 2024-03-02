function submitForm() {
    document.getElementById('actionButton').disabled = true;
    document.getElementById('myModal').style.display = 'block';
    document.getElementById('loading-container').style.display = 'block';
}

let e = document.getElementById("submit");
if(e != null)
e.addEventListener("keyup", function (event) {
        event.preventDefault();
        if (event.key === 'Enter') {
            submitForm()
        }
    });