import React, { useState } from 'react';
import { Card, Form, Input, Button, Tabs, message } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export const LoginPage = () => {
    const [activeTab, setActiveTab] = useState('login');
    const [loading, setLoading] = useState(false);

    const { authenticate } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (values) => {
        setLoading(true);
        try {
            await authenticate(activeTab, values.username, values.password);

            const successText = activeTab === 'login' ? 'Успешный вход!' : 'Успешная регистрация!';
            message.success(successText);

            navigate('/');
        } catch (error) {
            message.error(error.message || 'Ошибка авторизации');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh', background: '#f0f2f5' }}>
            <Card style={{ width: 400, boxShadow: '0 4px 12px rgba(0,0,0,0.5)' }}>
                <Tabs
                    activeKey={activeTab}
                    onChange={setActiveTab}
                    centered
                    items={[
                        { key: 'login', label: 'Вход' },
                        { key: 'register', label: 'Регистрация' },
                    ]}
                />
                <Form name="auth_form" onFinish={handleSubmit} layout="vertical" style={{ marginTop: 20 }}>
                    <Form.Item
                        name="username"
                        rules={[{ required: true, message: 'Введите имя пользователя!' }]}
                    >
                        <Input prefix={<UserOutlined />} placeholder="Имя пользователя" size="large" />
                    </Form.Item>

                    <Form.Item
                        name="password"
                        rules={[{ required: true, message: 'Введите пароль!' }]}
                    >
                        <Input.Password prefix={<LockOutlined />} placeholder="Пароль" size="large" />
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit" block size="large" loading={loading}>
                            {activeTab === 'login' ? 'Войти' : 'Зарегистрироваться'}
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
};