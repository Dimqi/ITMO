import React, {useRef, useState} from "react";
import { Card } from "primereact/card";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import "../../styles/MainPageStyles.css";
import {SingleSelectCheckbox} from "../SingleSelectCheckbox";
import Graph from "../Graph";
import ResultTable from "../ResultTable";
import { Toast } from 'primereact/toast';
import MainMenu from "../NavigationMenu";
import {checkHit} from "../../restApi/checkHitRequest";

export function MainPage({onLogout}) {

    const updateR = (r) => {
        localStorage.setItem("r", r);
        setR(r);
    }

    const updateY = (y) => {
        localStorage.setItem("y", y);
        setY(y);
    }

    const updateX = (x) => {
        localStorage.setItem("x", x);
        setX(x);
    }

    const [r, setR] = useState(()=>{
        const savedR =localStorage.getItem("r");
        return savedR === null ? 1: Number(savedR);
    });


    const [y, setY] = useState(() => {
        const savedY =localStorage.getItem("y");
        return savedY === null ? "": savedY;
    });

    const [x, setX] = useState(() => {
        const savedX =localStorage.getItem("x");
        return savedX === null ? "": Number(savedX);
    });


    const [results, setResults] = useState(() => {
        const saved = localStorage.getItem("results");
        return saved ? JSON.parse(saved) : [];
    });

    const toast = useRef(null);

    const addResult = (newResult) => {
        setResults(prev => {
            const updated = [...prev, newResult];
            localStorage.setItem("results", JSON.stringify(updated));
            return updated;
        });
    }

    const showToast = (summary, message, severity = "info") => {
        toast.current?.show({
            severity,
            summary: summary,
            detail: message,
            life: 3000
        });
    };

    const validateForm = (xStr, yStr, rStr) => {
        const yFl = parseFloat(yStr);
        const rFl = parseFloat(rStr);
        const xFl = parseFloat(xStr);

        if(isNaN(rFl) || rFl === null){
            showToast(
                "Ошибка ввода",
                "Ввыберите x",
                "error"
            );
            return false;
        }

        if(isNaN(xFl) || xFl === null){
            showToast(
                "Ошибка ввода",
                "Ввыберите x",
                "error"
            );
            return false;
        }


        if(isNaN(yFl) || yFl === null){
            showToast(
                "Ошибка ввода",
                "Введите число для y",
                "error"
            );
            return false;
        }

        if(yFl<-5 || yFl>5) {
            showToast(
                "Ошибка ввода",
                "Введите y от -5 до 5",
                "error"
            );
            return false;
        }
        return true;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if(!validateForm(x, y, r)) return;
        const res = await checkHit(x, y, r);

        if (!res) return;
        setResults(prevState => [...prevState, res]);

        toast.current.show({
            severity: "info",
            summary: "попадание",
            detail: res.hit,
            life: 3000
        });
        console.log(JSON.stringify(res));



    };

    return (
        <div id="MainPage">

            <Toast ref={toast}/>
            <MainMenu onLogout = {onLogout}/>
            <div id="form-and-svg-container">
                <Card className="coordinates-form-card">
                    <form onSubmit={handleSubmit} className="coordinates-form">

                        <h1>Форма для ввода</h1>

                        <SingleSelectCheckbox
                            label="X:"
                            options={[-4, -3, -2, -1, 0, 1, 2, 3, 4]}
                            value={x}
                            onChange={updateX}
                        />

                        <div style={{display: "flex", alignItems: 'center'}}>
                            <label style={{margin: '5px'}} htmlFor="y">Y:</label>
                            <InputText
                                id="y-input"
                                value={y}
                                onChange={(e) => updateY(e.target.value)}
                                className="input"
                                placeholder="Введите значение от -5 до 5"
                            />
                        </div>
                        <SingleSelectCheckbox
                            label="R:"
                            options={[-4, -3, -2, -1, 0, 1, 2, 3, 4]}
                            value ={r}
                            onChange={updateR}
                            isOptionDisabled={(value) => value <= 0}
                        />

                        <Button
                            label={"Проверить"}
                            type="submit"
                            className="submit-button"
                        />
                    </form>


                </Card>
                <Graph  r={r} showToast={showToast} addResult={addResult} results={results}/>
            </div>


            <ResultTable results={results} />
        </div>
    );
}
