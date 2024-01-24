import React, {useEffect, useState} from 'react';
import '../scss/Home.scss'
import {Navigation} from "../components/Navigation";
import {LeftSide} from "../components/LeftSide";
import {Col, Row} from "antd";
import {MDBBtn, MDBCard, MDBCardBody, MDBCardFooter, MDBCardHeader, MDBIcon} from "mdb-react-ui-kit";
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import AddInstructorModal from "../components/AddInstructorModal";
import axios from "axios";

interface IInstructorsData {
    id: number | undefined
    username: string | undefined,
    detail: string | undefined,
    email: string | undefined,
    department: string | undefined,
    role: string | undefined
}

const deleteInstructor = (instructorId: number | undefined) => {
    const token = localStorage.getItem('jwtToken')
    axios.defaults.headers['Authorization'] = token;
    const url = "http://localhost:8081/user/delete/".concat(instructorId ? instructorId.toString() : "");
    axios.delete(url, {})
        . then(response => {
            if (response.status == 200) {

            }
        })
        .catch(error => {
        })
}

const assignDepartmentManager = (instructorId: number | undefined) => {
    const token = localStorage.getItem('jwtToken')
    axios.defaults.headers['Authorization'] = token;
    const url = "http://localhost:8081/department/set-department-manager/1/".concat(instructorId ? instructorId.toString() : "");
    axios.put(url, {})
        . then(response => {
            if (response.status == 200) {

            }
        })
        .catch(error => {
        })
}

export const Instructors = () => {
    const [isAddInstructorOpen, setIsAddInstructorOpen] = useState(false);


    const [allInstructors, setAllInstructors] = useState<IInstructorsData[]>();
    const [allDepartmentManagers, setAllDepartmentManagers] = useState<IInstructorsData[]>()
    const [allDepartments, setAllDepartments] = useState<[]>()

    const [selectedInstructor, setSelectedInstructor] = useState<IInstructorsData>()

    useEffect(() => {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.get("http://localhost:8081/user/list/INSTRUCTOR", {})
            . then(response => {
                setAllInstructors(response.data);

            })
            .catch(error => {
            })
        axios.defaults.headers['Authorization'] = localStorage.getItem('jwtToken');
        axios.get("http://localhost:8081/user/list/DEPARTMENT_MANAGER", {})
            . then(response => {
                setAllDepartmentManagers(response.data);

            })
            .catch(error => {
            })
    }, []);

    const actionBodyTemplate = (rowData: IInstructorsData) => {
        return (
            <React.Fragment>
                <MDBBtn color='danger' tag='a' floating onClick={
                    () => deleteInstructor(rowData.id)} className={"m-1"}
                ><MDBIcon fas icon='trash'/></MDBBtn>
                <MDBBtn color='success' tag='a' floating onClick={
                    () => assignDepartmentManager(rowData.id)} className={"m-1"}
                ><MDBIcon fas icon='up-long'/></MDBBtn>
            </React.Fragment>
        );
    };

    return (
        <div>
            <Navigation></Navigation>
            <Row>
                <Col className={"col-3"}>
                    <LeftSide></LeftSide>
                </Col>
                <Col className={"col-9"}>
                    <MDBCard className={"m-2"}>
                        <MDBCardHeader className={"m-0 card-title"}>
                            <h5 className={"card-title"}>INSTRUCTORS</h5>
                        </MDBCardHeader>
                        <MDBCardBody className={"m-1 p-1"}>
                            <DataTable value={allInstructors?.concat(allDepartmentManagers ? allDepartmentManagers:[])} scrollable scrollHeight="400px" sortMode="single"
                                       tableStyle={{width: '100%'}}>
                                <Column field="username" header="Username" sortable
                                        headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column field="detail" header="Title" sortable
                                        headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column field="email" header="E-Mail" sortable
                                        headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column field="department" header="Department" sortable
                                        headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column field="role" header="Role" sortable
                                        headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column headerStyle={{backgroundColor: "#F8F9FA"}} body={actionBodyTemplate}></Column>
                            </DataTable>
                        </MDBCardBody>
                        <MDBCardFooter>
                            <MDBBtn className="m-1 w-25" onClick={() => setIsAddInstructorOpen(true)}>Add New
                                Instructor</MDBBtn>
                        </MDBCardFooter>
                    </MDBCard>
                </Col>
            </Row>
            {isAddInstructorOpen && (
                <AddInstructorModal
                    open={isAddInstructorOpen}
                    onClose={() => setIsAddInstructorOpen(false)}
                    departments={allDepartments}
                />
            )}
        </div>
    )
}
