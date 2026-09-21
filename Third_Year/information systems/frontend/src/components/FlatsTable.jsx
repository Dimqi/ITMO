import React from 'react';
import { Table, Button, Space, Popconfirm } from 'antd';
import { Link } from 'react-router-dom';
import { DeleteOutlined, EditOutlined } from '@ant-design/icons';

export const FlatsTable = ({ flats, loading, onEdit, onDelete }) => {

    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
            width: 50,
        },
        {
            title: 'Название',
            dataIndex: 'name',
            key: 'name',
            render: (text, record) => (
                <Link to={`/flats/${record.id}`} target="_blank" style={{ fontWeight: 500 }}>
                    {text}
                </Link>
            ),
        },
        {
            title: 'Цена ($)',
            dataIndex: 'price',
            key: 'price',
            render: (price) => (price ? `$${price.toLocaleString()}` : '—'),
        },
        {
            title: 'Площадь (м²)',
            dataIndex: 'area',
            key: 'area',
            render: (area) => (area ? `${area} м²` : '—'),
        },
        {
            title: 'Действия',
            key: 'actions',
            width: 180,
            render: (_, record) => (
                <Space size="middle">
                    <Button
                        type="default"
                        icon={<EditOutlined />}
                        onClick={() => onEdit(record.id)}
                    >
                        Изменить
                    </Button>

                    <Popconfirm
                        title="Удаление квартиры"
                        description="Вы уверены, что хотите удалить эту квартиру?"
                        onConfirm={() => onDelete(record.id)}
                        okText="Да"
                        cancelText="Нет"
                        okButtonProps={{ danger: true }}
                    >
                        <Button
                            type="default"
                            danger
                            icon={<DeleteOutlined />}
                        >
                            Удалить
                        </Button>
                    </Popconfirm>
                </Space>
            ),
        },
    ];

    return (
        <Table
            columns={columns}
            dataSource={flats}
            rowKey="id"
            loading={loading}
            pagination={false}
        />
    );
};