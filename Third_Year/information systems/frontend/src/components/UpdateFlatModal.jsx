import React from 'react';
import { Modal } from 'antd';
import { UpdateFlatForm } from './UpdateFlatForm';

export const UpdateFlatModal = ({
                                    open,
                                    onClose,
                                    initialData,
                                    onSubmit,
                                    loading = false
                                }) => {
    return (
        <Modal
            title="Редактирование данных о квартире"
            open={open}
            onCancel={onClose}
            footer={null}
            destroyOnClose
            width={800}
            centered
        >
            <UpdateFlatForm
                initialData={initialData}
                onSubmit={(data) => {
                    onSubmit(data);
                }}
                loading={loading}
            />
        </Modal>
    );
};