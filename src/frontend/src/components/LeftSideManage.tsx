import React, { useState, useRef } from 'react';
import '../scss/Home.scss';
import '../scss/LeftSideManage.scss';
import '../components/ModalNewSemester';
import { MDBBtn, MDBCard, MDBCardHeader, MDBCardBody, MDBCardFooter, MDBTypography, MDBIcon } from 'mdb-react-ui-kit';

export const LeftSideManage = () => {
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const fileInputRef = useRef<HTMLInputElement>(null);

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files.length > 0) {
            setSelectedFile(event.target.files[0]);
        }
    };

    const handleEditAvatarClick = () => {
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    };

    const handleUpload = () => {
        if (selectedFile) {
            console.log('Selected file:', selectedFile);
            // Perform the upload logic here
        }
    };

    return (
        <div>
            <MDBCard className="m-2 card-1">
                <MDBCardHeader className="m-0 card-title">
                    <h5 className="card-title">Edit Avatar</h5>
                </MDBCardHeader>
                <MDBCardBody className="m-0 p-0">
                    {selectedFile ? (
                        <img src={URL.createObjectURL(selectedFile)} alt="Avatar" className="avatar-image" />
                    ) : (
                        <MDBIcon fas icon="user-alt" size="8x" />
                    )}
                </MDBCardBody>
                <MDBCardFooter>
                    <input
                        type="file"
                        accept=".png"
                        onChange={handleFileChange}
                        className="d-none"
                        id="avatar-upload"
                        ref={fileInputRef}
                    />
                    <MDBBtn className="m-1 w-33" onClick={handleEditAvatarClick}>
                        Edit Avatar
                    </MDBBtn>
                    <MDBBtn className="m-1 w-33" onClick={handleUpload}>
                        Upload
                    </MDBBtn>
                </MDBCardFooter>
            </MDBCard>
        </div>
    );
};