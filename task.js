let persons = [];
let editIndex = null;

function renderTable() {
    const table = document.querySelector('.t1');
    // Remove all rows except the header
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    persons.forEach((person, idx) => {
        const row = table.insertRow();
        row.innerHTML = `
            <td>${person.name}</td>
            <td>${person.email}</td>
            <td>${person.age}</td>
            <td>${person.phone}</td>
            <td>${person.branch}</td>
            <td>${person.languages.join(', ')}</td>
            <td><button onclick="editPerson(${idx})">Edit</button><button onclick="deletePerson(${idx})">Delete</button></td>
        `;
    });
}

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('myForm');

    form.addEventListener('submit', function(event) {
        event.preventDefault();  

        const name = document.getElementById('name').value.trim();
        const email = document.getElementById('email').value.trim();
        const age = document.getElementById('age').value.trim();
        const phone = document.getElementById('phone').value.trim();
        let branch = '';
        if (document.getElementById('cse').checked) 
            branch = 'CSE';
        else if (document.getElementById('ece').checked) 
            branch = 'ECE';
        else if (document.getElementById('eee').checked) 
            branch = 'EEE';
        const languages = [];
        if (document.getElementById('english').checked) 
            languages.push('English');
        if (document.getElementById('hindi').checked) 
            languages.push('Hindi');
        if (document.getElementById('telugu').checked) 
            languages.push('Telugu');
        if (document.getElementById('french').checked) 
            languages.push('French');

        // Validation
        let errorMsg = '';
        const nameRegex = /^[A-Za-z ]+$/;
        const gmailRegex = /^\S+@gmail\.com$/;
        if (!name || !email || !age || !phone) {
            errorMsg = 'All fields are required.';
        } else if (!nameRegex.test(name)) {
            errorMsg = 'Name must contain only alphabetic characters.';
        } else if (!gmailRegex.test(email)) {
            errorMsg = 'Email must be a valid Gmail address (must end with @gmail.com).';
        } else if (!(Number(age) > 0)) {
            errorMsg = 'Age must be a positive number.';
        } else if (!/^\d{10}$/.test(phone)) {
            errorMsg = 'Phone number must be exactly 10 digits.';
        } else if (!branch) {
            errorMsg = 'Please select a branch.';
        } else if (languages.length === 0) {
            errorMsg = 'Please select at least one language.';
        }
        if (errorMsg) {
            alert(errorMsg);
            return;
        }

        const formData = {
            name,
            email,
            age,
            phone,
            branch,
            languages
        };
        if (editIndex !== null) {
            persons[editIndex] = formData;
            editIndex = null;
        } else {
            persons.push(formData);
        }
        renderTable();
        form.reset();
        // Reset radio and checkbox manually if needed
        document.querySelectorAll('input[type=radio], input[type=checkbox]').forEach(el => el.checked = false);
        form.querySelector('button[type=submit]').textContent = 'Submit';
    });
});

function deletePerson(idx) {
    persons.splice(idx, 1);
    renderTable();
}

function editPerson(idx) {
    const person = persons[idx];
    document.getElementById('name').value = person.name;
    document.getElementById('email').value = person.email;
    document.getElementById('age').value = person.age;
    document.getElementById('phone').value = person.phone;
    document.getElementById('cse').checked = person.branch === 'CSE';
    document.getElementById('ece').checked = person.branch === 'ECE';
    document.getElementById('eee').checked = person.branch === 'EEE';
    document.getElementById('english').checked = person.languages.includes('English');
    document.getElementById('hindi').checked = person.languages.includes('Hindi');
    document.getElementById('telugu').checked = person.languages.includes('Telugu');
    document.getElementById('french').checked = person.languages.includes('French');
    editIndex = idx;
    document.querySelector('#myForm button[type=submit]').textContent = 'Update';
}

console.log("Person is added!");
console.log(persons);