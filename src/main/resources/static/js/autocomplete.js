function fetchSuggestions() {
    const descriptionField = document.getElementById('description');
    const query = descriptionField.value.split(' ').pop();
    if (query.length < 2) {
        document.getElementById('suggestions').innerHTML = '';
        return;
    }
    fetch(`/notes/suggestions?query=${query}`)
        .then(response => response.json())
        .then(data => {
            const suggestionsBox = document.getElementById('suggestions');
            suggestionsBox.innerHTML = '';
            data.forEach(suggestion => {
                const div = document.createElement('div');
                div.classList.add('autocomplete-suggestion');
                div.innerText = suggestion;
                div.onclick = () => {
                    const words = descriptionField.value.split(' ');
                    words.pop();
                    words.push(suggestion);
                    descriptionField.value = words.join(' ');
                    suggestionsBox.innerHTML = '';
                };
                suggestionsBox.appendChild(div);
            });
        });
}

