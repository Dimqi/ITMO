let maxPage = null;
const loadingPages = new Set();
let toPast = false;

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
                
                const currentPages = JSON.parse(localStorage.getItem("currentPage") || "[1, 1]");
                let row = addResultToTable(data); 
                //
                if(currentPages[0]===1 || currentPages[1]===1){
                  
                  addFirstToTable(row);
                }  
                addNewResultToPage(data);
                //saveResultToStorage(data);
                
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

function addRowToTable(row, last = false, first = false){
  if(last){
    let tbody = document.querySelector("#results tbody");
  tbody.appendChild(row);
  }
  else if(first){
    
  }
}

window.addEventListener('load', () => {
    const currentPage = JSON.parse(localStorage.getItem("currentPage") || "[1, 1]");
    const lastPage = currentPage[1] || 1;
    loadNextPage(lastPage);
});


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
let flag = false;
const tbodyTableContainer = document.querySelector("#results tbody");
  tbodyTableContainer.addEventListener("scroll", () => {
    
    const scrollTop = tbodyTableContainer.scrollTop;
    const scrollHeight = tbodyTableContainer.scrollHeight;
    const clientHeight = tbodyTableContainer.clientHeight;
    
    if(Math.abs(scrollTop - lastScroll)>30){
      lastScroll = scrollTop;
    }
    
    if(scrollTop - clientHeight<=60  && (lastScroll>scrollTop) ){
      flag = true;
     
      let currentPages = JSON.parse(localStorage.getItem("currentPage") || "[1, 1]");
      if(currentPages[1] >2){
        
        console.log("пора подгрузить предыдущую страницу");
        let currentPages = JSON.parse(localStorage.getItem("currentPage") || "[1, 1]");
        let currentPage = currentPages[0];
        //if(currentPages[0] % 5 ==0 && currentPages[1] % 5 ==1 ) currentPage+=1;
        loadPreviousPage(currentPage-1);
        
        let parent = document.querySelector("#results tbody");
        if(parent.children.length >=60){
          cleanPageFromEndDOM();
          
        }
      }
      flag = false;
    }
    
    if(scrollTop + clientHeight >= scrollHeight - 260 && flag === false && (lastScroll<scrollTop)) {
      flag = true;
        console.log("пора подгрузить слудующую страницу");
        
        let currentPages = JSON.parse(localStorage.getItem("currentPage") || "[1, 1]");
        let currentPage = currentPages[1];
        if(maxPage == null){
        loadNextPage(currentPage+1);
      
        let parent = document.querySelector("#results tbody");
        if(parent.children.length >=60){
          cleanPageFromStartDOM();
        }
      }
      flag = false;
    }
    
    
    
  });
});

async function loadPageFromDB(pageNumber, callback){  
  if (loadingPages.has(pageNumber)) return; 
    loadingPages.add(pageNumber);
  
  try {
    const resp = await fetch(`/fcgi-app?action=${pageNumber}`);
    const data = await resp.json();

    console.log("Данные из БД:", data);

    if (data && data.length > 0) {
      cleanLocalStorage();
      splitResultsIntoPages(data, pageNumber);
      console.log("данные сохранены");
      
      if (typeof callback === "function") {
        if(toPast === true) {
          toPast = false;
          callback(pageNumber+4);
         }
        else callback(pageNumber);
       }
    } else {
      console.log("Нет данных в БД");
      showToast("Достигнут конец таблицы", "error");
      maxPage = pageNumber;
    }
  } catch (err) {
    showToast("Ошибка загрузки данных: " + err, "error");

  }
  loadingPages.delete(pageNumber);
  return;  
}

function loadNextPage(pageNumber){
  let results = JSON.parse(localStorage.getItem(`results_page_${pageNumber}`) || "[]");
  
  if(results.length === 0){
    loadPageFromDB(pageNumber, loadNextPage);
    return;
  }
  
  results = results.map(r => typeof r === "string" ? JSON.parse(r) : r);
  results.forEach(result => addLastToTable(addResultToTable(result)));

  let currentPage = JSON.parse(localStorage.getItem("currentPage") || "[1, 1]");
  currentPage[0] = currentPage[1];
  currentPage[1] = pageNumber;
  localStorage.setItem("currentPage", JSON.stringify(currentPage));
  console.log("текущая страница:" + currentPage);
}

function loadPreviousPage(pageNumber){
  let results = JSON.parse(localStorage.getItem(`results_page_${pageNumber}`) || "[]");
  
  if(results.length === 0){
    toPast = true;
    maxPage = null;
    loadPageFromDB(pageNumber-4, loadPreviousPage);
    return;
  }
  
  results = results.map(r => typeof r === "string" ? JSON.parse(r) : r);
  results.reverse().forEach(result => addFirstToTable(addResultToTable(result)));
  
  let currentPage = JSON.parse(localStorage.getItem("currentPage") || "[1, 1]");
  currentPage[1] = currentPage[0];
  currentPage[0] = pageNumber;
  localStorage.setItem("currentPage", JSON.stringify(currentPage));
  console.log("текущая страница:" + currentPage);
}

function cleanLocalStorage(){
  for (let i = localStorage.length - 1; i >= 0; i--) {
    const key = localStorage.key(i);
    if (key.startsWith("results_page_")) {
        localStorage.removeItem(key);
    }
  }
}

function splitResultsIntoPages(results, currentPage) {
    const pageSize = 20;
    const totalPages = Math.ceil(results.length / pageSize);
    if(currentPage===0) currentPage+=1;
    for (let i = 0; i < totalPages; i++) {
        const pageData = results.slice(i * pageSize, (i + 1) * pageSize);
        
        localStorage.setItem(`results_page_${i+currentPage}`, JSON.stringify(pageData));
    }
}


function cleanPageFromStartDOM(){
  const tbody = document.querySelector("#results tbody");

  for (let i = 0; i < 20 && tbody.children.length > 0; i++) {
    tbody.removeChild(tbody.children[0]);
  }
}

function cleanPageFromEndDOM(){
  const tbody = document.querySelector("#results tbody");

  for (let i = 0; i < 20 && tbody.children.length > 0; i++) {
    tbody.removeChild(tbody.lastElementChild);
  }

}

function addNewResultToPage(row){
  const pageNumber = 1;
  let pageData = JSON.parse(localStorage.getItem(`results_page_${pageNumber}`) || "[]");
  if(pageData.length !==0){
    pageData.unshift(row);  
    localStorage.setItem(`results_page_${pageNumber}`, JSON.stringify(pageData));
  }  
}