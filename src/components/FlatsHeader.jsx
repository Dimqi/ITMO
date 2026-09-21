import React, { useState, useEffect } from 'react';
import { Card, Row, Col, Statistic, Select, Tag, Space, Typography, Spin, message } from 'antd';
import { DollarOutlined } from '@ant-design/icons';
import api from '../api/axios.js';

const { Text } = Typography;

const VIEW_OPTIONS = [
    { label: 'YARD', value: 'YARD' },
    { label: 'PARK', value: 'PARK' },
    { label: 'BAD', value: 'BAD' },
    { label: 'NORMAL', value: 'NORMAL' },
];

export const FlatsHeader = () => {
    const [totalPrice, setTotalPrice] = useState(0);
    const [uniqueViews, setUniqueViews] = useState([]);
    const [selectedView, setSelectedView] = useState('YARD');
    const [countByView, setCountByView] = useState(0);

    const [loadingTotal, setLoadingTotal] = useState(true);
    const [loadingViews, setLoadingViews] = useState(true);
    const [loadingCount, setLoadingCount] = useState(false);

    useEffect(() => {
        const fetchTotalPrice = async () => {
            try {
                setLoadingTotal(true);
                const response = await api.get('/flat/sumPrices');
                setTotalPrice(response.data);
            } catch (error) {
                message.error("Ошибка расчёта суммарной стоимости");
            } finally {
                setLoadingTotal(false);
            }
        };

        fetchTotalPrice();
    }, []);

    useEffect(() => {
        const fetchUniqueViews = async () => {
            try {
                setLoadingViews(true);
                const response = await api.get('/flat/distinctView');
                setUniqueViews(response.data);
            } catch (error) {
                message.error("Ошибка получения уникальных View");
            } finally {
                setLoadingViews(false);
            }
        };

        fetchUniqueViews();
    }, []);

    useEffect(() => {
        if (!selectedView) return;

        const fetchCountByView = async () => {
            try {
                setLoadingCount(true);
                const response = await api.get(`/flat/count/${selectedView}`);
                setCountByView(response.data);
            } catch (error) {
                message.error(`Ошибка получения количества для ${selectedView}`);
            } finally {
                setLoadingCount(false);
            }
        };

        fetchCountByView();
    }, [selectedView]);

    return (
        <Card style={{ marginBottom: 24 }}>
            <Row gutter={[24, 16]} align="middle">
                <Col xs={24} sm={8}>
                    <Spin spinning={loadingTotal}>
                        <Statistic
                            title="Суммарная стоимость всех объектов"
                            value={totalPrice}
                            precision={0}
                            prefix={<DollarOutlined />}
                            suffix="$"
                        />
                    </Spin>
                </Col>

                <Col xs={24} sm={8}>
                    <Text type="secondary" style={{ display: 'block', marginBottom: 4 }}>
                        Количество объектов по виду:
                    </Text>
                    <Space>
                        <Select
                            value={selectedView}
                            onChange={(val) => setSelectedView(val)}
                            style={{ width: 160 }}
                            placeholder="Выберите вид"
                            options={VIEW_OPTIONS}
                        />
                        <Spin spinning={loadingCount}>
                            <Tag color="blue" style={{ fontSize: 14, padding: '4px 8px' }}>
                                {countByView} шт.
                            </Tag>
                        </Spin>
                    </Space>
                </Col>

                <Col xs={24} sm={8}>
                    <Text type="secondary" style={{ display: 'block', marginBottom: 4 }}>
                        Уникальные виды:
                    </Text>
                    <Spin spinning={loadingViews}>
                        <Space wrap>
                            {uniqueViews.length > 0 ? (
                                uniqueViews.map((view) => (
                                    <Tag key={view} color="cyan">
                                        {view}
                                    </Tag>
                                ))
                            ) : (
                                <Text type="secondary">Нет данных</Text>
                            )}
                        </Space>
                    </Spin>
                </Col>
            </Row>
        </Card>
    );
};