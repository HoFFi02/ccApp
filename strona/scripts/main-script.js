let draggedElement;

function allowDrop(event) {
    event.preventDefault();
}

document.addEventListener('DOMContentLoaded', () => {
// Fetch data from your API endpoint for recipes
fetch('/main/main')
    .then(response => response.json())
    .then(data => {
        const recipesTableBody = document.getElementById('recipes-body');
        const daysTableBody = document.getElementById('days-body');

        data.recipes.forEach(recipe => {
            const row = document.createElement('tr');
            row.draggable = true;
            row.className = 'draggable';
            row.innerHTML = `
                <td>${recipe.id_przepisy}</td>
                <td>${recipe.nazwa}</td>
                <td>${recipe.sposob_przygotowania}</td>
            `;
            row.ondragstart = (event) => dragStart(event, recipe);
            row.ondragend = () => dragEnd();
            recipesTableBody.appendChild(row);
        });

        data.days.forEach(day => {
            const row = document.createElement('tr');
            row.className = 'day-cell';
          //  row.dataset.dayId = day.id_dzien;
          row.id = `day-cell-${day.id_dzien}`;
            row.innerHTML = `
                <td>${day.numer_dnia}</td>
                <td>${day.nazwa_przepisu || 'Brak przepisu'}</td>
                <td>${day.sposob_przygotowania_przepisu || 'Brak przepisu'}</td>
                <td><button class="btn btn-danger" onclick="deleteDay(${day.numer_dnia}, ${day.id_przepisu})"><ion-icon name="trash-outline"></ion-icon></button></td>
            `;
            // nie mam drugiego argumentu, jak go przeslac??????
            row.ondrop = (event) => dropToDays(event, day.numer_dnia);
            row.ondragover = allowDrop;
            daysTableBody.appendChild(row);
        });

        // Event listener for the entire document
        document.addEventListener('drop', drop);
        document.addEventListener('dragover', allowDrop);

        // Event listener for the days table
        daysTableBody.addEventListener('drop', dropToDays);
        daysTableBody.addEventListener('dragover', allowDrop);
    })
    .catch(error => console.error('Error fetching data:', error));

const selectedRecipes = {};

function dragStart(event, recipe) {
    
    draggedElement = event.target;
    event.dataTransfer.setData('text/plain', JSON.stringify(recipe));
    draggedElement.classList.add('dragged');
    
    console.log("start");
}

function dragEnd() {
}


function drop(event, dayId) {
    event.preventDefault();

    const data = event.dataTransfer.getData('text/plain');
    const recipe = JSON.parse(data);

    const dayRow = document.getElementById(`day-cell-${dayId}`);

    console.log("o e");

    if (dayRow) {
        if (!selectedRecipes[dayId]) {
            const recipeRow = document.createElement('tr');
            recipeRow.innerHTML = `
                <td>${recipe.id_przepisy}</td>
                <td>${recipe.nazwa}</td>
                <td>${recipe.sposob_przygotowania}</td>
            `;
            dayRow.appendChild(recipeRow);
            selectedRecipes[dayId] = recipe;
        }
    }

    draggedElement.classList.remove('dragged');
}

function dropToDays(event, dayNumber) {
event.preventDefault();

const data = event.dataTransfer.getData('text/plain');
const recipe = JSON.parse(data);

const dayRow = document.getElementById(`day-cell-${dayNumber}`);

console.log("dropujemu");
console.log("dayrow"+dayRow);

if (dayRow) {
    console.log('Dropped on day:', dayNumber, 'Recipe:', recipe);

    // Dodaj console.log, aby sprawdzić, czy zapytanie jest wysyłane
    console.log('Sending request to update day...');

      
console.log(recipe.id_przepisy);
    // Tutaj dodaj kod wysyłający zapytanie do serwera
    updateDayWithRecipe(dayNumber, recipe.id_przepisy);
    addProdcutIntoShoppingList(recipe.id_przepisy);   // wszytkie dane sa w przepisy_produkty

    // Pobierz aktualne informacje o dniu
        
      dayRow.innerHTML = `
            <td>${dayNumber}</td>
            <td>${recipe.nazwa}</td>
            <td>${recipe.sposob_przygotowania}</td>
            <td><button class="btn btn-danger" onclick="deleteDay(${dayNumber}, ${recipe.id_przepisy})"><ion-icon name="trash-outline"></ion-icon></button></td>
        `;
        console.log(recipe.id_przepisy);
    draggedElement.classList.remove('dragged');
}
}

function updateDayWithRecipe(dayId, recipeId) {
// Wykonaj zapytanie do serwera, używając fetch lub innej metody
fetch('/main/main', {
    method: 'PUT',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify({ dayId, recipeId }),
})
.then(response => response.json())
.then(data => {
    console.log('Day updated:', data);
    
})
.catch(error => console.error('Error updating day:', error));
}






});
function deleteDay(dayNumber, recipeId) {
   
    fetch(`/main/delete-day/${dayNumber}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => response.json())
    .then(data => {
        console.log('Recipe deleted from day ', dayNumber);
        // Dodaj kod do zaktualizowania widoku po usunięciu dnia
        
        deleteFromShoppingList(recipeId);

    const dayRow = document.getElementById(`day-cell-${dayNumber}`);
    dayRow.innerHTML = `
    <td>${dayNumber}</td>
    <td>${'Brak przepisu'}</td>
    <td>${'Brak przepisu'}</td>
    <td><button class="btn btn-danger" onclick="deleteDay(${dayNumber}, , ${recipeId})"><ion-icon name="trash-outline"></ion-icon></button></td>
`;
    })
    .catch(error => console.error('Error deleting day:', error));
}

function addProdcutIntoShoppingList(przepisyId) {
   
    fetch(`/main/shopping-list`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({przepisyId}),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Products added to shopping list ');
    })
    .catch(error => console.error('Error deleting day:', error));
}

function deleteFromShoppingList(recipeId) {
   
    fetch(`/main/delete-products-from-shopping-list/${recipeId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({recipeId}),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Products deleted from shopping list ');
        // Dodaj kod do zaktualizowania widoku po usunięciu dnia
    
    })
    .catch(error => console.error('Error deleting day:', error));
}