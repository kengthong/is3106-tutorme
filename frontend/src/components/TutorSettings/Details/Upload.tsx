import React, {useEffect, useState} from 'react';
import {message, Upload} from 'antd';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import {BACKEND_BASE_URL} from "../../../config/constants";
import {UserService} from "../../../services/User";



const ProfileAvatar = (props:any) => {
    const [loading, setLoading ] = useState(false);
    const [imageUrl, setImageUrl] = useState("");

    useEffect(() => {
        setImageUrl(props.imgUrl || "")
    }, [])
    const uploadImage = async(base64: string) => {
        const response = await UserService.uploadImage(base64);
        if(response.ok){
            setImageUrl(base64);
            message.success("Unable to upload image");
        } else {
            message.error("Unable to upload image");
        }
    }
    const getBase64 = (img: any, callback:any) => {
        const reader = new FileReader();
        reader.addEventListener('load', () => callback(reader.result));
        reader.readAsDataURL(img);
    }

    const beforeUpload = (file: any) => {
        const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
        if (!isJpgOrPng) {
            message.error('You can only upload JPG/PNG file!');
        }
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
            message.error('Image must smaller than 2MB!');
        }
        console.log('file =', file)
        if( isJpgOrPng && isLt2M) {
            getBase64(file, (cb: any) => {
                console.log('cb =', cb)
                uploadImage(cb)
            })
        }
        return isJpgOrPng && isLt2M;
    }
    const handleChange = (info:any) => {
        if (info.file.status === 'uploading') {
            setLoading(true);

            return;
        }
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, (imageUrl:any) => {
                setImageUrl(imageUrl);
                setLoading(false);
            });
        }
    };

    const uploadButton = (
        <div>
            {loading ? <LoadingOutlined /> : <PlusOutlined />}
            <div style={{ marginTop: 8 }}>Upload</div>
        </div>
    );
    return (
        <Upload
            name="avatar"
            listType="picture-card"
            className="avatar-uploader"
            showUploadList={false}
            // action={`${BACKEND_BASE_URL}/person/uploadImage`}
            beforeUpload={beforeUpload}
            onChange={handleChange}
        >
            {imageUrl ? <img src={imageUrl} alt="avatar" style={{ width: '100%' }} /> : uploadButton}
        </Upload>
    );
}

export default ProfileAvatar;