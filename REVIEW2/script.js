const output = document.getElementById('output');

function displayAvailableBikes() {
    output.innerHTML = '<p>Displaying available bikes... (Mock Data: Bike 1, Bike 2, Bike 3)</p>';
}

function rentBikes() {
    const name = document.getElementById('rentName').value;
    const count = document.getElementById('rentCount').value;
    if (name && count > 0) {
        output.innerHTML = `<p>${name} has rented ${count} bike(s). Rental successful!</p>`;
    } else {
        output.innerHTML = '<p class="text-danger">Please enter valid details to rent bikes.</p>';
    }
}

function returnBikes() {
    const name = document.getElementById('returnName').value;
    if (name) {
        output.innerHTML = `<p>${name} has returned the rented bike(s). Bill generated: $100</p>`;
    } else {
        output.innerHTML = '<p class="text-danger">Please enter your name to return bikes.</p>';
    }
}
