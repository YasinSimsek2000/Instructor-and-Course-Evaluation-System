import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import {MDBBtn, MDBIcon, MDBModalContent, MDBModalDialog} from 'mdb-react-ui-kit';
import '../scss/ModalNewSemester.scss';
import {DatePicker} from 'antd';
import axios from "axios";
import {ITerm} from "./LeftSide";

type ModalNewSemesterProps = {
    open: boolean;
    children: React.ReactNode;
    onClose: () => void;
};

const ModalNewSemester = ({open, children, onClose}: ModalNewSemesterProps) => {
    const [name, setName] = useState<string | undefined>();
    const [termType, setTermType] = useState<string | undefined>();
    const [startDate, setStartDate] = useState<string | undefined>();
    const [endDate, setEndDate] = useState<string | undefined>();
    const [, setTerm] = useState<ITerm | undefined>();
    const [, setBool1] = useState<boolean>(true)
    if (!open) return null;

    const portalElement = document.getElementById('portal');

    if (!portalElement) {
        return null;
    }

    const handleSave = () => {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.post("http://localhost:8081/term/update", {
            name,
            startDate,
            endDate,
            termType
        })
            . then(response => {
                if (response.status == 200) {
                    setTerm({
                        termId: -1,
                        name: response.data.name,
                        startDate: response.data.startDate,
                        endDate: response.data.endDate,
                        termType: response.data.termType
                    });

                } else {
                    alert(response.data)
                }
            });
    };

    return ReactDOM.createPortal(
        <div className="overlay">
            <MDBModalDialog className="modal-dialog-centered1">
                <MDBModalContent style={{backgroundColor: 'white'}}>
                    <div>
                        {children}
                        <div className="date-picker-container">
                            <div className="date-picker-wrapper">
                                <DatePicker
                                    getPopupContainer={(triggerNode: HTMLElement): HTMLElement => triggerNode.parentNode as HTMLElement}
                                    onChange={date => {
                                        setStartDate(date === null ? undefined : date.format("YYYY-MM-DD"));
                                    }}/>
                            </div>
                            <div className="date-picker-wrapper">
                                <DatePicker
                                    getPopupContainer={(triggerNode: HTMLElement): HTMLElement => triggerNode.parentNode as HTMLElement}
                                    onChange={date => {
                                        setEndDate(date === null ? undefined : date.format("YYYY-MM-DD"));
                                    }}/>
                            </div>
                        </div>
                        <div className="dropdown-container">
                            <div className="dropdown">
                                <select className="form-select" aria-label="Semester"
                                        onChange={(event) => setTermType(event.target.value)}>
                                    <option defaultValue={0}></option>
                                    <option>FALL</option>
                                    <option>SPRING</option>
                                    <option>SUMMER</option>
                                </select>
                            </div>
                            <div className="dropdown">
                                <select className="form-select"
                                        aria-label="Year"
                                        onChange={
                                            event => setName(event.target.value)
                                        }
                                >
                                    <option defaultValue={2023}></option>
                                    <option>2022</option>
                                    <option>2021</option>
                                    <option>2020</option>
                                    <option>2019</option>
                                    <option>2018</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div className="button-container">
                        <MDBBtn onClick={a => {
                            onClose();
                        }
                        } className="close-button">Close</MDBBtn>
                        <MDBBtn onClick={save => {
                            handleSave();
                            onClose();
                        }} className="save-button">
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

export default ModalNewSemester;
