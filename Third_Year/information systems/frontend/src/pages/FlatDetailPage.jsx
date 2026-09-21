import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Card, Descriptions, Tag, Button, Spin, Result, Space, Typography, message, Popconfirm } from 'antd';
import { ArrowLeftOutlined, HomeOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { UpdateFlatModal } from '../components/UpdateFlatModal';
import api from '../api/axios.js'; // 1. Импортируем ваш Axios клиент

const { Title } = Typography;

export const FlatDetailPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [flat, setFlat] = useState(null);
    const [loading, setLoading] = useState(true);

    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [submitting, setSubmitting] = useState(false);
    const [deleteLoading, setDeleteLoading] = useState(false);

    useEffect(() => {
        const fetchFlatDetails = async () => {
            setLoading(true);
            try {
                const response = await api.get(`/flat/${id}`);
                setFlat(response.data); // Записываем реальный DTO с бэкенда
            } catch (error) {
                message.error('Не удалось загрузить данные о квартире');
            } finally {
                setLoading(false);
            }
        };

        if (id) {
            fetchFlatDetails();
        }
    }, [id]);

    const handleUpdateSubmit = async (updatedFields) => {
        setSubmitting(true);
        try {
            const response = await api.put(`/flat/${flat.id}`, updatedFields);

            const updatedData = response.data || { ...flat, ...updatedFields };
            setFlat(updatedData);

            message.success('Данные квартиры успешно обновлены!');
            setIsEditModalOpen(false); // Закрываем модалку
        } catch (error) {
            message.error('Ошибка при сохранении изменений');
        } finally {
            setSubmitting(false);
        }
    };

    const handleDelete = async () => {
        setDeleteLoading(true);
        try {
            await api.delete(`/flat/${flat.id}`);
            message.success('Квартира успешно удалена');
            navigate('/main');
        } catch (error) {
            message.error('Не удалось удалить квартиру');
        } finally {
            setDeleteLoading(false);
        }
    };

    if (loading) {
        return (
            <div style={{ display: 'flex', justifyContent: 'center', padding: 50 }}>
                <Spin size="large" tip="Загрузка карточки..." />
            </div>
        );
    }

    if (!flat) {
        return (
            <Result
                status="404"
                title="Квартира не найдена"
                extra={<Button type="primary" onClick={() => navigate('/main')}>Вернуться к списку</Button>}
            />
        );
    }

    return (
        <div style={{ maxWidth: 900, margin: '0 auto', padding: '12px 0' }}>
            <Button
                icon={<ArrowLeftOutlined />}
                onClick={() => navigate('/main')}
                style={{ marginBottom: 16 }}
            >
                Назад к списку
            </Button>

            <Card
                title={
                    <Space size="middle">
                        <HomeOutlined style={{ color: '#1890ff', fontSize: 24 }} />
                        <Title level={3} style={{ margin: 0 }}>{flat.name}</Title>
                    </Space>
                }
                extra={
                    <Space size="middle">
                        <Button
                            type="primary"
                            icon={<EditOutlined />}
                            onClick={() => setIsEditModalOpen(true)}
                        >
                            Редактировать
                        </Button>

                        <Popconfirm
                            title="Удаление квартиры"
                            description="Вы уверены, что хотите удалить эту квартиру?"
                            onConfirm={handleDelete}
                            okText="Да, удалить"
                            cancelText="Отмена"
                            okButtonProps={{ danger: true, loading: deleteLoading }}
                        >
                            <Button
                                danger
                                type="default"
                                icon={<DeleteOutlined />}
                            >
                                Удалить
                            </Button>
                        </Popconfirm>
                    </Space>
                }
            >
                <Descriptions title="Основная информация" bordered column={{ xs: 1, sm: 2 }}>
                    <Descriptions.Item label="ID объекта">{flat.id}</Descriptions.Item>
                    <Descriptions.Item label="Название">{flat.name || '—'}</Descriptions.Item>

                    <Descriptions.Item label="Цена">${flat.price?.toLocaleString()}</Descriptions.Item>
                    <Descriptions.Item label="Площадь">{flat.area} м²</Descriptions.Item>

                    <Descriptions.Item label="Количество комнат">{flat.numberOfRooms || '—'}</Descriptions.Item>
                    <Descriptions.Item label="Балкон">
                        <Tag color={flat.balcony ? 'green' : 'default'}>
                            {flat.balcony ? 'Есть' : 'Нет'}
                        </Tag>
                    </Descriptions.Item>

                    <Descriptions.Item label="До метро">{flat.timeToMetroOnFoot ? `${flat.timeToMetroOnFoot} мин` : '—'}</Descriptions.Item>
                    <Descriptions.Item label="Координаты">
                        {flat.coordinates ? `X: ${flat.coordinates.x}, Y: ${flat.coordinates.y}` : '—'}
                    </Descriptions.Item>

                    <Descriptions.Item label="Отделка">{flat.furnish || '—'}</Descriptions.Item>
                    <Descriptions.Item label="Вид">{flat.view || '—'}</Descriptions.Item>

                    <Descriptions.Item label="Транспорт">{flat.transport || '—'}</Descriptions.Item>
                    <Descriptions.Item label="ID дома">{flat.houseId || '—'}</Descriptions.Item>

                    <Descriptions.Item label="Дата создания" span={2}>
                        {flat.creationDate ? new Date(flat.creationDate).toLocaleDateString() : '—'}
                    </Descriptions.Item>
                </Descriptions>
            </Card>

            <UpdateFlatModal
                open={isEditModalOpen}
                onClose={() => setIsEditModalOpen(false)}
                initialData={flat}
                onSubmit={handleUpdateSubmit}
                loading={submitting}
            />
        </div>
    );
};