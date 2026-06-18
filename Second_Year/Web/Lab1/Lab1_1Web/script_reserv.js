document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    if (!form) return;

    let selectedR = null;

    const rButtons = document.querySelectorAll('.r-btn');
    rButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            rButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            selectedR = btn.dataset.value;
            console.log("Выбран R =", selectedR);
        });
    });

    document.querySelectorAll('input[name="x"]').forEach(cb => {
        cb.addEventListener('change', () => {
            if (cb.checked) {
                document.querySelectorAll('input[name="x"]').forEach(other => {
                    if (other !== cb) other.checked = false;
                });
            }
        });
    });

form.addEventListener("submit", function(e) {
        e.preventDefault();

        const selectedXCheckbox = document.querySelector('input[name="x"]:checked');
        if (!selectedXCheckbox) {
            showToast("Выберите значение X.", "error");
            return;
        }
        const xRaw = selectedXCheckbox.value;

        const yInput = document.getElementById("change_y");
        let yRaw = yInput.value.trim();

        if (yRaw === "") {
            showToast("Заполните поле Y.", "error");
            return;
        }
  

        if (!selectedR) {
            showToast("Выберите значение R.", "error");
            return;
        }
        const rRaw = selectedR;

        
        
        yRaw = yRaw.replace(",", ".");
  
        const x = Number(xRaw);
        const r = Number(rRaw);
        
        let y;
        try {
            y = new Decimal(yRaw);
        } catch (e) {
            showToast("Введите корректное число для Y.", "error");
            return;
        }

        if (!Number.isFinite(x) || y.isNaN() || !Number.isFinite(r)) {
            showToast("Введите корректные числовые значения.", "error");
            return;
        }

        if (r < 1 || r > 3) {
            showToast("R должен быть в диапазоне от 1 до 3.", "error");
            return;
        }

        if (y.lt(-5) || y.gt(5)) {
            showToast("Y должен быть в диапазоне от -5 до 5.", "error");
            return;
        }
        
        let startTime = Date.now();
        fetch(`/fcgi-app?change_x=${x}&change_y=${y.toString()}&change_r=${r}`)
            .then(resp => resp.json()) 
            .then(data => {
                console.log("Ответ сервера:", data);
                data.hit ? showToast("Попадание","success") : showToast("Промах");
                let row = addResultToTable(data); 
                addFirstToTable(row);
                saveResultToStorage(data);
                
            })
            .catch(err => showToast("Ошибка: " + err, "error"));
    });
});

function addResultToTable(result) {
    const json = JSON.stringify(result);
    // Размер в байтах (UTF-8)
    const bytes = new TextEncoder().encode(json).length;
    console.log(bytes, "байт");
  
    let tbody = document.querySelector("#results tbody");
    if (!tbody) {
        console.error("Таблица результатов не найдена");
        return;
    }
    
    let row = document.createElement("tr");
    row.innerHTML = `
        <td>${result.x}</td>
        <td>${result.y}</td>
        <td>${result.r}</td>
        <td style="color:${result.hit ? "green" : "red"}">
            ${result.hit ? "Попадание" : "Мимо"}
        </td>
        <td>${result.currentTime}</td>
        <td>${result.execTime} ms</td>
    `;
    return row;
    
}

function addFirstToTable(row){
  let tbody = document.querySelector("#results tbody");
  tbody.insertBefore(row, tbody.firstChild);
}

function addLastToTable(row){
  let tbody = document.querySelector("#results tbody");
  tbody.appendChild(row);
}

function loadResultsFromStorage() {
    let tbody = document.querySelector("#results tbody");
    if (!tbody) return;

    let results = JSON.parse(localStorage.getItem("results") || "[]");

    if (results.length === 0) {
      console.log("fetch вызван");
        fetch("/fcgi-app?action=get_all")
            .then(resp => resp.json())
            .then(data => {
          
                console.log("Данные из БД:", data);
                if (data && data.length > 0) {
                    localStorage.setItem("results", JSON.stringify(data));
                    console.log("данные сохранены");
                    loadResultsFromStorage();
                } else {
                    console.log("Нет данных в БД");
                    showToast("Нет данных для отображения", "error");
                }
            })
            .catch(err => showToast("Ошибка загрузки данных: " + err, "error"));
        return;
    }

    results = results.map(r => typeof r === "string" ? JSON.parse(r) : r);

    tbody.innerHTML = "";
    results.forEach(result => addFirstToTable(addResultToTable(result))); 
}


function saveResultToStorage(result) {
    let results = JSON.parse(localStorage.getItem("results") || "[]");
    results.push(result);
    localStorage.setItem("results", JSON.stringify(results));
}


window.addEventListener('load', loadResultsFromStorage);

function showToast(message, status = "error") {
  const container = document.querySelector("#toast-container tbody");
  const row = document.createElement("tr");
  const cell = document.createElement("td");
  
  if(status === "error"){
    cell.setAttribute("class", "error");
  }
  else if(status === "success"){
    cell.setAttribute("class", "success");
  }
  
  cell.innerText = message;
  row.appendChild(cell);
  container.appendChild(row);

  setTimeout(() => {
    row.remove();
  }, 4000);
}




document.addEventListener("DOMContentLoaded", () => {
let lastScroll = 0;
const tbodyTableContainer = document.querySelector("#results tbody");
  tbodyTableContainer.addEventListener("scroll", () => {
    
    const scrollTop = tbodyTableContainer.scrollTop;
    const scrollHeight = tbodyTableContainer.scrollHeight;
    const clientHeight = tbodyTableContainer.clientHeight;
    
    if(Math.abs(scrollTop - lastScroll)>30){
      lastScroll = scrollTop;
    }
    
    if(scrollTop - clientHeight<=60  && (lastScroll>scrollTop) ){
      console.log("пора подгрузить предыдущую страницу");
    }
    
    if(scrollTop + clientHeight >= scrollHeight - 60) {
      console.log("пора подгрузить слудующую страницу");
    }
    
    
    
  });
});



function loadNextPage(pageNumber){
  let results = JSON.parse(localStorage.getItem(`results_page_${number+1}`) || "[]");
  results = results.map(r => typeof r === "string" ? JSON.parse(r) : r);
  results.ForEach(result => addLastToTable(addResultToTable(result)));
}

function loadPreviousPage(pageNumber){
  let results = JSON.parse(localStorage.getItem(`results_page_${number-1}`) || "[]");
  results = results.map(r => typeof r === "string" ? JSON.parse(r) : r);
  results.ForEach(result => addFirstToTable(addResultToTable(result)));
}