import React from 'react';
import { Layout, theme } from 'antd';
import { Outlet } from 'react-router-dom';
import { AppHeader } from '../components/AppHeader.jsx';

const { Content } = Layout;

export const MainLayout = () => {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();

    return (
        <Layout style={{ minHeight: '100vh' }}>
            <AppHeader />

            <Content style={{ padding: '24px' }}>
                <div
                    style={{
                        background: colorBgContainer,
                        minHeight: 280,
                        padding: 24,
                        borderRadius: borderRadiusLG,
                    }}
                >
                    <Outlet />
                </div>
            </Content>
        </Layout>
    );
};