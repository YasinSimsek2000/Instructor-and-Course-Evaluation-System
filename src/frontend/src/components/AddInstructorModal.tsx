import React, {useState} from 'react';
import {IDepartmentData} from "../pages/Home";
import "../scss/AddInstructorModal.scss";
import {
    MDBBtn,
    MDBIcon,
    MDBInput,
    MDBModalBody,
    MDBModalContent,
    MDBModalDialog,
    MDBModalFooter,
    MDBModalHeader,
    MDBTypography
} from "mdb-react-ui-kit";
import ReactDOM from "react-dom";
import axios from "axios";

interface AddInstructorModalProps {
    open: boolean;
    onClose: () => void;
    departments: IDepartmentData[] | undefined;
}

const AddInstructorModal: React.FC<AddInstructorModalProps> = ({ open, onClose, departments }) => {
    const portalElement = document.getElementById('portal');

    const [fullName, setFullName] = useState('');
    const [email, setEmail] = useState('');
    const [title, setTitle] = useState('');
    const [selectedDepartment, setSelectedDepartment] = useState('');

    const handleSave = () => {

        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.post("http://localhost:8081/user/save", {
            username: fullName,
            detail: title,
            email: email,
            role: "INSTRUCTOR"
        })

        onClose();
    };

    if (!portalElement) {
        return null; // Return null if the portal element is not found
    }

    return ReactDOM.createPortal(
        <div className="overlay">
            <MDBModalDialog className="modal-dialog-centered">
                <MDBModalContent style={{ backgroundColor: 'white' }}>
                    <MDBModalHeader>
                        <MDBTypography tag="div" className="display-6">Add Instructor</MDBTypography>
                    </MDBModalHeader>
                    <MDBModalBody>
                        <div className="input-container">
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Full Name"
                                    value={fullName}
                                    onChange={(e) => setFullName(e.target.value)}
                                />
                            </div>
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Title"
                                    value={title}
                                    onChange={(e) => setTitle(e.target.value)}
                                />
                            </div>
                        </div>
                        <div className="input-container">
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>
                            <div className="input-wrapper">
                                <div className="select-container">
                                    <select
                                        className="form-select"
                                        aria-label="Department"
                                        value={selectedDepartment}
                                        onChange={(e) => setSelectedDepartment(e.target.value)}
                                    >
                                        <option value="" disabled>
                                            Select Department
                                        </option>
                                        {Array.from(new Set(departments?.map((department) => department.department))).map((uniqueDepartment, index) => (
                                            <option key={index} value={uniqueDepartment}>
                                                {uniqueDepartment}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                        </div>
                    </MDBModalBody>
                    <MDBModalFooter>
                        <MDBBtn onClick={onClose} className="close-button">
                            Close
                        </MDBBtn>
                        <MDBBtn className="save-button" onClick={handleSave}>
                            <MDBIcon fas icon="save" />
                            <span className="button-text">Save</span>
                        </MDBBtn>
                    </MDBModalFooter>
                </MDBModalContent>
            </MDBModalDialog>
        </div>,
        portalElement
    );
};

export default AddInstructorModal;
