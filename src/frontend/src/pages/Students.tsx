import React, {useEffect, useState} from 'react';
import '../scss/Home.scss';
import {Navigation} from '../components/Navigation';
import {LeftSide} from '../components/LeftSide';
import {Col, Row} from 'antd';
import {MDBBtn, MDBCard, MDBCardBody, MDBCardFooter, MDBCardHeader, MDBIcon} from 'mdb-react-ui-kit';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import AddNewStudentModal from "../components/AddNewStudentModal";
import axios from 'axios';
import {IDepartmentData} from "./Home";

export interface IStudent {
    id: number | undefined,
    name: string | undefined,
    email: string | undefined,
    department: string | undefined
}

interface AddStudentModalProps {
    open: boolean;
    onClose: () => void;
    departments: IDepartmentData[];
}

const deleteStudent = (studentId: number | undefined) => {
    const token = localStorage.getItem('jwtToken')
    axios.defaults.headers['Authorization'] = token;
    const url = "http://localhost:8081/user/delete/".concat(studentId ? studentId.toString() : "");
    axios.delete(url, {})
        . then(response => {
            if (response.status == 200) {

            }
        })
        .catch(error => {
        })
};

export const Students = () => {
    const [isAddStudentOpen, setIsAddStudentOpen] = useState(false);

    const [allStudents, setAllStudents] = useState<IStudent[] | undefined>();

    useEffect(() => {
        // Fetch student data from API
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.get('http://localhost:8081/user/list/STUDENT')
            . then(response => {
                setAllStudents(response.data);

            })
            .catch(error => {
                //
            });
    }, []);

    const actionBodyTemplate = (rowData: IStudent) => {
        return (
            <React.Fragment>
                <MDBBtn color='danger' tag='a' floating className={"m-1"}
                        onClick={
                    student => deleteStudent(rowData.id)
                        }
                ><MDBIcon fas icon='trash'/></MDBBtn>
            </React.Fragment>
        );
    };

    // Get unique departments

    return (
        <div>
            <Navigation />
            <Row>
                <Col className="col-3">
                    <LeftSide />
                </Col>
                <Col className="col-9">
                    <MDBCard className="m-2">
                        <MDBCardHeader className="m-0 card-title">
                            <h5 className="card-title">STUDENTS</h5>
                        </MDBCardHeader>
                        <MDBCardBody className="m-1 p-1">
                            <DataTable
                                value={allStudents}
                                scrollable
                                scrollHeight="400px"
                                sortMode="single"
                                tableStyle={{ width: '100%' }}
                            >
                                <Column field="username" header="username" sortable headerStyle={{ backgroundColor: '#F8F9FA' }}></Column>
                                <Column field="email" header="E-Mail" sortable headerStyle={{ backgroundColor: '#F8F9FA' }}></Column>
                                <Column
                                    field="departmentId"
                                    header="Department"
                                    sortable
                                    headerStyle={{ backgroundColor: '#F8F9FA' }}
                                ></Column>
                                <Column body={actionBodyTemplate}></Column>
                            </DataTable>
                        </MDBCardBody>
                        <MDBCardFooter>
                            <MDBBtn className="m-1 w-25" onClick={() => setIsAddStudentOpen(true)}>
                                Add New Student
                            </MDBBtn>
                        </MDBCardFooter>
                    </MDBCard>
                </Col>
            </Row>
            {isAddStudentOpen && (
                <AddNewStudentModal
                    open={isAddStudentOpen}
                    onClose={() => setIsAddStudentOpen(false)}
                    departments={allStudents}
                />
            )}

        </div>
    );
};
