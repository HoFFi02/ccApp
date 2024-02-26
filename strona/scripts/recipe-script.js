
document.addEventListener('DOMContentLoaded', () => {
    // Fetch data from your API endpoint for recipes
    fetch('/recipe/recipe')
        .then(response => response.json())
        .then(data => {
            const recipesTableBody = document.getElementById('recipes-body');
    
            data.recipes.forEach(recipe => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${recipe.id_przepisy}</td>
                    <td>${recipe.nazwa}</td>
                    <td>${recipe.sposob_przygotowania}</td>
                    <td>${recipe.products ? recipe.products.map(product => product.skladniki.replace(/,/g, '<br>')).join('') : ''}</td>
                   
                `; 
                
                recipesTableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching data:', error));
})