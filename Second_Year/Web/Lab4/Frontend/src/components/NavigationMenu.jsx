import { Sidebar } from "primereact/sidebar";
import { Menu } from "primereact/menu";
import { Button } from "primereact/button";
import { useState } from "react";
import "../styles/navigationMenuStyles.css";


export default function SidebarMenu({onLogout}) {
    const [visible, setVisible] = useState(false);

    const items = [
        {
            label: "Пример",
            icon: "pi pi-home",
        },
        {
            label: "Пример",
            icon: "pi pi-chart-line",
        },

        {
            label: "Пример",
            icon: "pi pi-table",
        },
        {
            separator: true
        },
        {
            label: "Выход",
            icon: "pi pi-sign-out",
            command: () => {
                onLogout();
            }
        }
    ];

    return (
        <>
            <Button
                icon="pi pi-bars"
                label="Меню"
                onClick={() => setVisible(true)}
            />

            <Sidebar visible={visible} onHide={() => setVisible(false)}>
                <Menu model={items} className="custom-menu"/>
            </Sidebar>
        </>
    );
}
