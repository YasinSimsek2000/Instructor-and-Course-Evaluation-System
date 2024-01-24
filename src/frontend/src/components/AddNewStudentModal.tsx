import React, {useState} from 'react';
import "../scss/AddNewStudentModal.scss";
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
import {IStudent} from "../pages/Students";

interface AddNewStudentModalProps {
    open: boolean;
    onClose: () => void;
    departments: IStudent[] | undefined;
}

const AddNewStudentModal: React.FC<AddNewStudentModalProps> = ({open, onClose, departments}) => {
    const portalElement = document.getElementById('portal');

    const [studentNumber, setStudentNumber] = useState('');
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [selectedDepartment, setSelectedDepartment] = useState('');



    const handleSave = () => {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.post("http://localhost:8081/user/save", {
            username: name,
            detail: studentNumber,
            email: email,
            role: "STUDENT",
        })
        onClose();
    };

    if (!portalElement) {
        return null; // Return null if the portal element is not found
    }

    return ReactDOM.createPortal(
        <div className="overlay">
            <MDBModalDialog className="modal-dialog-centered">
                <MDBModalContent style={{backgroundColor: 'white'}}>
                    <MDBModalHeader>
                        <MDBTypography tag="div" className="display-6">Add Student</MDBTypography>
                    </MDBModalHeader>
                    <MDBModalBody>
                        <div className="input-container">
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Student Number"
                                    value={studentNumber}
                                    onChange={(e) => setStudentNumber(e.target.value)}
                                />
                            </div>
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Name"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
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
                            {/*<div className="input-wrapper">*/}
                            {/*    <div className="select">*/}
                            {/*        <select*/}
                            {/*            className="form-select"*/}
                            {/*            aria-label="Department"*/}
                            {/*            value={selectedDepartment}*/}
                            {/*            onChange={(e) => setSelectedDepartment(e.target.value)}*/}
                            {/*        >*/}
                            {/*            ))*/}
                            {/*        </select>*/}
                            {/*    </div>*/}
                            {/*</div>*/}
                        </div>
                    </MDBModalBody>
                    <MDBModalFooter>
                        <MDBBtn onClick={onClose} className="close-button m-1">
                            Close
                        </MDBBtn>
                        <MDBBtn className="save-button m-1" onClick={handleSave}>
                            <MDBIcon fas icon="save"/>
                            <span className="button-text">Save</span>
                        </MDBBtn>
                    </MDBModalFooter>
                </MDBModalContent>
            </MDBModalDialog>
        </div>,
        portalElement
    );
};

export default AddNewStudentModal;
