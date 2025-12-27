import { Sidebar } from "primereact/sidebar";
import { Menu } from "primereact/menu";
import { Button } from "primereact/button";
import { useState } from "react";



export default function SidebarMenu({onLogout}) {
    const [visible, setVisible] = useState(false);

    const items = [
        {
            label: "Главная",
            icon: "pi pi-home",
            //command: () => navigate("/")
        },
        {
            label: "График",
            icon: "pi pi-chart-line",
            //command: () => navigate("/graph")
        },

        {
            label: "Таблица",
            icon: "pi pi-table",
            //command: () => navigate("/table")
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
