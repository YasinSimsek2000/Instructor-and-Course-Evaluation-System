import React, {useState} from 'react';
import "../scss/AddCourseModal.scss";
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

interface AddCourseModalProps {
    open: boolean;
    onClose: () => void;
}



const AddCourseModal: React.FC<AddCourseModalProps> = ({open, onClose}) => {
    const portalElement = document.getElementById('portal');

    const [courseName, setCourseName] = useState('');
    const [departmentCode, setDepartmentCode] = useState('');
    const [courseCode, setCourseCode] = useState('');
    const [theoraticalCredits, setTheoraticalCredits] = useState('');
    const [practicalCredits, setPracticalCredits] = useState('');
    const [ectsCredits, setEctsCredits] = useState('');
    const [courseType, setCourseType] = useState('');

    const handleSave = () => {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.post("http://localhost:8081/course/save", {
            name: courseName,
            departmentCode: departmentCode,
            courseCode: courseCode,
            type: courseType,
            creditT: theoraticalCredits,
            creditP: practicalCredits,
            creditEcts: ectsCredits
        })
            . then(response => {
                if (response.status === 200) {

                }
            })
            .catch(error => {
                //
            });
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
                        <MDBTypography tag="div" className="display-6">Add Course</MDBTypography>
                    </MDBModalHeader>
                    <MDBModalBody>
                        <div className="input-container">
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Course Name"
                                    value={courseName}
                                    onChange={(e) => setCourseName(e.target.value)}
                                />
                            </div>
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Department Code"
                                    value={departmentCode}
                                    onChange={(e) => setDepartmentCode(e.target.value)}
                                />
                            </div>
                        </div>
                        <div className="input-container">
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Course Code"
                                    value={courseCode}
                                    onChange={(e) => setCourseCode(e.target.value)}
                                />
                            </div>
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Theo. Credits"
                                    value={theoraticalCredits}
                                    onChange={(e) => setTheoraticalCredits(e.target.value)}
                                />
                            </div>
                        </div>
                        <div className="input-container">
                            <div className="input-wrapper">
                                <MDBInput
                                    label="Practical Credits"
                                    value={practicalCredits}
                                    onChange={(e) => setPracticalCredits(e.target.value)}
                                />
                            </div>
                            <div className="input-wrapper">
                                <MDBInput
                                    label="ECTS Credits"
                                    value={ectsCredits}
                                    onChange={(e) => setEctsCredits(e.target.value)}
                                />
                            </div>
                        </div>
                        <div className="select-container">
                            <div className="select-wrapper">
                                <div className="select">
                                    <select
                                        className="form-select"
                                        aria-label="Department"
                                        value={courseType}
                                        onChange={(e) => setCourseType(e.target.value)}
                                    >
                                        <option value="" disabled>
                                            Course Type
                                        </option>
                                        <option>
                                            MANDATORY
                                        </option>
                                        <option>
                                            ELECTIVE
                                        </option>
                                    </select>
                                </div>
                            </div>
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

export default AddCourseModal;
