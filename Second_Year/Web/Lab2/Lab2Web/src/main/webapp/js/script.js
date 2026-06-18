let maxPage = null;
const loadingPages = new Set();
let toPast = false;

document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    if (!form) return;

	
	const rDiv = document.querySelector('div[data-r]');
	let selectedR = rDiv ? rDiv.dataset.r : null;
	if (selectedR && selectedR !== "0") {
		const rButtons = document.querySelectorAll('.r-btn');
	    rButtons.forEach(btn => {
	    if (btn.dataset.value === selectedR) {
	           btn.classList.add('active');
	    } else {
	           btn.classList.remove('active');
	    	}
	    });
	    console.log("По умолчанию выбран R =", selectedR);
		updateSvgScale(selectedR);
		replaceR(selectedR);
	}
	
	
    const rButtons = document.querySelectorAll('.r-btn');
    rButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            rButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            selectedR = btn.dataset.value;
            console.log("Выбран R =", selectedR);			
			replaceR(selectedR);
			updateSvgScale(selectedR);
        });
    });
	
	
	form.addEventListener("submit", function(e) {
        e.preventDefault();

        const selectedX = document.querySelector('select[name="x"]');
		
        if (!selectedX || !selectedX.value) {
            showToast("Выберите значение X.", "error");
            return;
        }
        const xRaw = selectedX.value;

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

        if (y.lt(-3) || y.gt(5)) {
            showToast("Y должен быть в диапазоне от -3 до 5.", "error");
            return;
        }
		
		
		const params = new URLSearchParams();
		params.append("x", x);
		params.append("y", y.toString());
		params.append("r", r);
		
		
		fetch("/Lab2Web/controller",{ 
		            method: "Post",
		            headers: {
		             "Content-Type": "application/x-www-form-urlencoded"
		             },
		            body: params.toString()
		         })
		           .then(response => response.text())
				   .then(html => {
						document.open();
					   	document.write(html);
					   	document.close();
				   });
			
    });
	
	
	const svg = document.querySelector("svg");
	svg.addEventListener("click", event =>{
		
		const rInput = selectedR;
		if (!rInput) {
		    showToast("Выберите значение R.", "error");
		    return;
		}		
		
		
		const center = 275;
		const scale = 50; 
		console.log(rInput);
		const rect = svg.getBoundingClientRect();
		
		const x = (event.clientX - rect.left - center) / scale;
		const y = (center - (event.clientY - rect.top)) / scale;
		
		console.log(x);
		console.log(y);
		const r = parseFloat(rInput);
		
		if(y<-3 || y>5 || x<-5 || x>3){
			showToast("Клик по области со значениями вне диапозона");
			return;
		}
		
		const params = new URLSearchParams();
		params.append("x", x);
		params.append("y", y.toString());
		params.append("r", r);		
				
		fetch("/Lab2Web/controller",{ 
				method: "Post",
				headers: {
				"Content-Type": "application/x-www-form-urlencoded"
				},
				body: params.toString()
				})
				.then(response => response.text())
				.then(html => {
				document.open();
				document.write(html);
				document.close();
		
		
		});
		
	});
	
});

function replaceR(r){
	let halfRPosTexts = document.querySelectorAll("text.r-half-pos");
	halfRPosTexts.forEach(text => {
		text.textContent = parseFloat(r)/2;
	});
	
	let rPosTexts = document.querySelectorAll("text.r-pos");
		rPosTexts.forEach(text => {
			text.textContent = r;
	});
	
	let halfRNegTexts = document.querySelectorAll("text.r-half-neg");
		halfRNegTexts.forEach(text => {
			text.textContent = parseFloat(-r)/2;
	});
		
	let rNegTexts = document.querySelectorAll("text.r-neg");
		rNegTexts.forEach(text => {
			text.textContent = -r;
	});
}



function updateSvgScale(selectedR) {
    const svg = document.querySelector("svg");
    if (!svg) return;

    const center = 275;
    const baseScale = 50;
    const scale = baseScale * parseFloat(selectedR); 

    const rect = svg.querySelector("rect");
    if (rect) {
        rect.setAttribute("x", center);
        rect.setAttribute("y", center);
        rect.setAttribute("width", scale);
        rect.setAttribute("height", scale);
    }

    const path = svg.querySelector("path");
    if (path) {
        path.setAttribute("d", `M${center},${center} L${center - scale},${center} A${scale},${scale} 0 0,0 ${center},${center + scale}`);
    }

    const polygon = svg.querySelector("polygon");
    if (polygon) {
        polygon.setAttribute("points", `${center},${center} ${center},${center - scale/2} ${center + scale},${center}`);
    }
	
	svg.querySelectorAll('line[data-value]').forEach(line => {
	        const value = parseFloat(line.dataset.value);
	        if (line.dataset.axis === 'x') {
	            line.setAttribute('x1', center + value * scale);
	            line.setAttribute('x2', center + value * scale);
	        } else {
	            line.setAttribute('y1', center - value * scale);
	            line.setAttribute('y2', center - value * scale);
	        }
	    });

	svg.querySelectorAll('text[data-value]').forEach(text => {
	        const value = parseFloat(text.dataset.value);
	        if (text.dataset.axis === 'x') {
	            text.setAttribute('x', center + value * scale);
	        } else {
	            text.setAttribute('y', center - value * scale);
	        }
	    });
			
	svg.querySelectorAll("circle").forEach(point =>{
		console.log("до:", point.getAttribute("fill"));
		console.log("перерисовка точек");
		
		let dataX = parseFloat(point.dataset.x);
		console.log(dataX);
		let dataY = parseFloat(point.dataset.y);
		
		console.log(dataY);
		let hit = checkHit(dataX, dataY, parseFloat(selectedR));
		
		if(hit){
			point.setAttribute("fill", "green");
		}
		else{
			point.setAttribute("fill", "red");
		}
		console.log("после:", point.getAttribute("fill"));
	});	
		
	
	
}


function checkHit(x, y, r){
	
	console.log("проверка попадания");
	let hit =false; 
	if(x >= 0 && x <= r && y <= 0 && y >= -r) {hit =true;}
	if(x >= 0 && x <= r && y >= 0 && y <= r/2 - x/2) {hit =true;}
	if(x <= 0 && y <= 0 && x >= -r && y >= -r && x*x + y*y <= r*r) {hit =true;}
	
	return hit;
}


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


