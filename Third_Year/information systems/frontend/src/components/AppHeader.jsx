import React from 'react';
import { Layout, Button, Space, Typography } from 'antd';
import { UserOutlined, LogoutOutlined, HomeOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const { Header } = Layout;
const { Title } = Typography;

export const AppHeader = () => {
    const navigate = useNavigate();
    const { logout } = useAuth();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <Header
            style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                padding: '0 24px',
                background: '#001529', // Тёмно-синяя шапка по умолчанию в Antd
                boxShadow: '0 2px 8px rgba(0,0,0,0.15)',
            }}
        >
            <div
                style={{ display: 'flex', alignItems: 'center', gap: 10, cursor: 'pointer' }}
                onClick={() => navigate('/')}
            >
                <HomeOutlined style={{ fontSize: 24, color: '#1890ff' }} />
                <Title level={4} style={{ color: '#fff', margin: 0 }}>
                    Real Estate App
                </Title>
            </div>

            <Space size="middle">
                <Button
                    type="text"
                    icon={<UserOutlined />}
                    onClick={() => navigate('/profile')}
                    style={{ color: '#fff' }}
                >
                    Профиль
                </Button>

                <Button
                    type="primary"
                    danger
                    icon={<LogoutOutlined />}
                    onClick={handleLogout}
                >
                    Выйти
                </Button>
            </Space>
        </Header>
    );
};