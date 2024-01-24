import React, {useEffect, useState} from 'react';
import '../scss/Home.scss'
import {Navigation} from "../components/Navigation";
import {LeftSide} from "../components/LeftSide";
import {Col, Row} from "antd";
import {MDBCard, MDBCardBody, MDBCardHeader} from "mdb-react-ui-kit";
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import axios from "axios";

interface IDepartmentManagersData {
    username: string,
    detail: string,
    email: string,
    department: string
}

export const DepartmentManagers = () => {
    const [allDepartmentManagers, setAllDepartmentManagers] = useState<IDepartmentManagersData[]>();

    useEffect(() => {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.get("http://localhost:8081/user/list/DEPARTMENT_MANAGER", {})
            . then(response => {
                setAllDepartmentManagers(response.data);

            })
            .catch(error => {
            })
    }, []);


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
                            <h5 className={"card-title"}>DEPARTMENT MANAGERS</h5>
                        </MDBCardHeader>
                        <MDBCardBody className={"m-1 p-1"}>
                            <DataTable value={allDepartmentManagers} scrollable scrollHeight="400px" sortMode="single" tableStyle={{ width: '100%'}}>
                                <Column field="username" header="Username" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column field="detail" header="Title" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column field="email" header="E-Mail" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column field="department" header="Department" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                            </DataTable>
                        </MDBCardBody>
                    </MDBCard>
                </Col>
            </Row>
        </div>
    )
}
