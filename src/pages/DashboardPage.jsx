import React, {useEffect, useState} from 'react';
import { message } from 'antd';
import { FlatsTable } from '../components/FlatsTable';
import { UpdateFlatModal } from '../components/UpdateFlatModal';
import {FlatsHeader} from "../components/FlatsHeader.jsx";
import api from "../api/axios.js";


export const DashboardPage = () => {
    const [flats, setFlats] = useState([]); // Твой массив кратких объектов квартир

    const [flatsLoading, setFlatsLoading] = useState(false); // Загрузка всего списка
    const [tableLoading, setTableLoading] = useState(false); // Операция над строкой (удаление)

    // Состояния для модалки и дозагрузки данных
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [modalLoading, setModalLoading] = useState(false);
    const [selectedFlatFullData, setSelectedFlatFullData] = useState(null);

    useEffect(() => {
        const fetchFlatData = async () => {
            setFlatsLoading(true);
            try {
                const response = await api.get('/flat/all');
                const shortFlats = response.data.map(({ id, name, price, area }) => ({
                    id,
                    name,
                    price,
                    area
                }));
                setFlats(shortFlats);

            } catch (error) {
                message.error('Ошибка при загрузке списка квартир');
            }
            finally {
                setFlatsLoading(false);
            }
        };

        fetchFlatData();
    }, []);

    const handleEditClick = async (flatId) => {
        setIsEditModalOpen(true);
        setModalLoading(true);

        try {
            const response = await api.get(`/flat/${flatId}`);
            setSelectedFlatFullData(response.data); // Записываем данные
        } catch (error) {
            message.error("Ошибка получения данных квартиры");
            setIsEditModalOpen(false);
        } finally {
            setModalLoading(false);
        }
    };


    const handleDeleteClick = async (flatId) =>{
        setTableLoading(true);
        try {
            const response = await api.delete(`/flat/${flatId}`);
            if (response.status.valueOf() === 204){
                setFlats((prevFlats) => prevFlats.filter((flat) => flat.id !== flatId));
                message.success("Квартира успешно удалена из списка");
            }else{
                message.error("Ошибка получения данных квартиры");
            }

        } catch (error) {
            message.error("Ошибка получения данных квартиры");
        } finally {
            setTableLoading(false);
        }
    };

    const handleFormSubmit = (updatedPayload) => {
        setModalLoading(true);

        setTimeout(() => {
            setFlats((prevFlats) =>
                prevFlats.map((flat) =>
                    flat.id === selectedFlatFullData.id
                        ? { ...flat, ...updatedPayload }
                        : flat
                )
            );

            setModalLoading(false);
            setIsEditModalOpen(false);
            setSelectedFlatFullData(null);
            message.success('Квартира успешно обновлена!');
        }, 500);
    };

    const handleCloseModal = () => {
        setIsEditModalOpen(false);
        setSelectedFlatFullData(null);
    };


    return (
        <div>
            <FlatsHeader></FlatsHeader>
            <FlatsTable
                flats={flats}
                loading={flatsLoading || tableLoading}
                onEdit={handleEditClick}
                onDelete={handleDeleteClick}
            />

            <UpdateFlatModal
                open={isEditModalOpen}
                onClose={handleCloseModal}
                initialData={selectedFlatFullData} // Передаем полные загруженные данные
                onSubmit={handleFormSubmit}
                loading={modalLoading} // Передаем состояние загрузки в модалку
            />
        </div>
    );
};