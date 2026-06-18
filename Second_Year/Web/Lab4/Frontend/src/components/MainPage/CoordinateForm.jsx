import { Card } from "primereact/card";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { SingleSelectCheckbox } from "../SingleSelectCheckbox";
import "../../styles/coordinateFormStyles.css";

export default function CoordinateForm({
                                           x, y, r,
                                           onXChange, onYChange, onRChange,
                                           onSubmit
                                       }) {
    return (
        <Card className="coordinates-form-card">
            <form onSubmit={onSubmit} className="coordinates-form">
                <h1>Форма для ввода</h1>

                <SingleSelectCheckbox
                    label="X:"
                    options={[-4, -3, -2, -1, 0, 1, 2, 3, 4]}
                    value={x}
                    onChange={onXChange}
                />

                <div style={{ display: "flex", alignItems: "center" }}>
                    <label htmlFor="y">Y:</label>
                    <InputText
                        id="y"
                        value={y}
                        onChange={(e) => onYChange(e.target.value)}
                        placeholder="от -5 до 5"
                    />
                </div>

                <SingleSelectCheckbox
                    label="R:"
                    options={[-4, -3, -2, -1, 0, 1, 2, 3, 4]}
                    value={r}
                    onChange={onRChange}
                    isOptionDisabled={(v) => v <= 0}
                />

                <Button label="Проверить" type="submit" />
            </form>
        </Card>
    );
}
