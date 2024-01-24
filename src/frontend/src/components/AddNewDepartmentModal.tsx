import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import {MDBBtn, MDBIcon, MDBInput, MDBModalContent, MDBModalDialog} from 'mdb-react-ui-kit';
import '../scss/AddNewDepartmentModal.scss';
import axios from "axios";

type ModalNewSemesterProps = {
    open: boolean;
    children: React.ReactNode;
    onClose: () => void;
    departments: IDepartmentData[] | undefined;
};

export interface IDepartmentData {
    id: number | undefined
    name: string | undefined,
    departmentCode: string | undefined,
    facultyName: string | undefined,
    manager: string | undefined,
}

const AddNewDepartmentModal = ({open, children, onClose, departments}: ModalNewSemesterProps) => {
    const [name, setName] = useState('');
    const [departmentCode, setDepartmentCode] = useState('');
    const [facultyName, setFacultyName] = useState('');

    if (!open) return null;
    const uniqueFaculties = Array.from(new Set(departments?.map((department) => department.facultyName)));
    const portalElement = document.getElementById('portal');

    if (!portalElement) {
        return null;
    }

    const handleSave = () => {

        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.post("http://localhost:8081/department/save", {
            name,
            departmentCode: departmentCode,
            facultyName: facultyName
        })
             . then(response => {
                if (response.status == 200) {

                    onClose();
                }
            })
            .catch(error => {
            })

        onClose(); // Close the modal
    };


    return ReactDOM.createPortal(
        <div className="overlay">
            <MDBModalDialog className="modal-dialog-centered2">
                <MDBModalContent style={{backgroundColor: 'white'}}>
                    <div>
                        {children}
                        <div className="input-container">
                            <div className="input-wrapper">
                                <MDBInput label="Name" value={name} onChange={(e) => setName(e.target.value)}/>
                            </div>
                        </div>
                        <div className="input-container">
                            <div className="input-wrapper">
                                <MDBInput label="Code" value={departmentCode}
                                          onChange={(e) => setDepartmentCode(e.target.value)}/>
                            </div>
                        </div>
                        <div className="input-container">
                            <div className="input-wrapper">
                                <MDBInput label="Faculty" value={facultyName}
                                          onChange={(e) => setFacultyName(e.target.value)}/>
                            </div>
                        </div>
                    </div>
                    <div className="button-container">
                        <MDBBtn onClick={onClose} className="close-button">
                            Close
                        </MDBBtn>
                        <MDBBtn onClick={handleSave} className="save-button">
                            <MDBIcon fas icon="save"/>
                            <span className="button-text">Save</span>
                        </MDBBtn>
                    </div>
                </MDBModalContent>
            </MDBModalDialog>
        </div>,
        portalElement
    );
};

export default AddNewDepartmentModal;
