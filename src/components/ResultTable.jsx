import React from "react";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import "../styles/resultTableStyles.css";

export default function ResultTable({results}) {

    const hitBody = (row) => {
        return (
            <span className={row.hit ? "hit" : "miss"}>
                {row.hit ? "Попадание" : "Мимо"}
            </span>
        );
    };

    return (
        <div id="result-table">
            <DataTable value={results}  tableStyle={{ minWidth: "300px"}} stripedRows>
                <Column field="x" header="X"/>
                <Column field="y" header="Y"/>
                <Column field="r" header="R"/>
                <Column header="Попадание" body={hitBody} />
            </DataTable>
        </div>
    );
}
