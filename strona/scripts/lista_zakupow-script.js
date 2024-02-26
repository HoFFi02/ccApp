document.addEventListener('DOMContentLoaded', () => {
    // Fetch data from your API endpoint for the shopping list
    fetch('/lista_zakupow/lista_zakupow')
        .then(response => response.json())
        .then(data => {
            const shoppingListBody = document.getElementById('shopping-list-body');

            data.shoppingList.forEach(item => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${item.przepisy_produkty_id_przepisy_produkty}</td>
                    <td>${item.nazwa_skladnika}</td>
                    <td>${item.ilosc}</td>
                    <td>${item.jednostka}</td>
                `;
                
                shoppingListBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching data:', error));
});