import React, {useState} from 'react';
import '../scss/Home.scss';
import {Navigation} from '../components/Navigation';
import {LeftSide} from '../components/LeftSide';
import {Col, Row} from 'antd';
import AddNewDepartmentModal from '../components/AddNewDepartmentModal';
import {MDBBtn, MDBCard, MDBCardBody, MDBCardFooter, MDBCardHeader, MDBIcon, MDBTypography,} from 'mdb-react-ui-kit';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {Button} from 'primereact/button';
import {useNavigate} from "react-router-dom";
import axios from "axios";

export interface IDepartmentData {
    id: number | undefined
    name: string | undefined,
    departmentCode: string | undefined,
    facultyName: string | undefined,
    manager: string | undefined,
    department: string | undefined
}

const deleteDepartment = (departmentId: number | undefined) => {
    const token = localStorage.getItem('jwtToken')
    axios.defaults.headers['Authorization'] = token;
    const url = "http://localhost:8081/department/delete/".concat(departmentId ? departmentId.toString() : "");
    axios.delete(url, {})
        . then(response => {
            if (response.status == 200) {

            }
        })
        .catch(error => {
        })
}

export const Home = () => {

    const [isAddNewDepartmentOpen, setIsAddNewDepartmentOpen] = useState(false);
    const [newDepartmentName, setNewDepartmentName] = useState<string>();
    const [newDepartmentCode, setNewDepartmentCode] = useState<string>();
    const [newDepartmentFaculty, setNewDepartmentFaculty] = useState<string>();

    const [allDepartments, setAllDepartments] = useState<IDepartmentData[]>();

    const [selectedDepartment, setSelectedDepartment] = useState<IDepartmentData>();

    const [departmentDialog, setDepartmentDialog] = useState<boolean>(false);
    const navigate = useNavigate();

    const departmentDialogFooter = (
        <React.Fragment>
            <Button label="Cancel" icon="pi pi-times" outlined onClick={() => setDepartmentDialog(false)}/>
            <Button label="Save" icon="pi pi-check" onClick={saveDepartment}/>
        </React.Fragment>
    );

    const handleRowClick = (p: (event: any) => void) => {
    };

    function saveDepartment() {
        //todo
    }
    const token = localStorage.getItem('jwtToken')
    axios.defaults.headers['Authorization'] = token;
    axios.get("http://localhost:8081/department/list", {})
        . then(response => {
            setAllDepartments(response.data);
        })
        .catch(error => {
        })

    const actionBodyTemplate = (rowData: IDepartmentData) => {
        return (
            <React.Fragment>
                <MDBBtn color='danger' tag='a' floating onClick={
                    () => deleteDepartment(rowData.id)} className={"m-1"}
                ><MDBIcon fas icon='trash'/></MDBBtn>
            </React.Fragment>
        );
    };

    return (
        <div>
            <Navigation/>
            <Row>
                <Col className={'col-3'}>
                    <LeftSide/>
                </Col>
                <Col className={"col-9"}>
                    <MDBCard className={"m-2"}>
                        <MDBCardHeader className={"m-0 card-title"}>
                            <h5 className={"card-title"}>DEPARTMENTS</h5>
                        </MDBCardHeader>
                        <MDBCardBody className={"m-1 p-1"}>
                            <DataTable
                                value={allDepartments}
                                scrollable
                                scrollHeight="400px"
                                sortMode="multiple"
                                tableStyle={{width: '100%'}}
                            >
                                <Column field="name" header="Name" sortable
                                        headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column field="departmentCode" header="Dep. Code" sortable
                                        headerStyle={{backgroundColor: "#F8F9FA", width: "8%"}}></Column>
                                <Column field="facultyName" header="Faculty" sortable
                                        headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column field="manager" header="Manager" sortable
                                        headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                <Column body={actionBodyTemplate}></Column>
                            </DataTable>
                        </MDBCardBody>
                        <MDBCardFooter>
                            <MDBBtn className="m-1 w-50" onClick={() => setIsAddNewDepartmentOpen(true)}>
                                Add New Department
                            </MDBBtn>
                        </MDBCardFooter>
                    </MDBCard>
                </Col>
            </Row>
            {isAddNewDepartmentOpen && (
                <AddNewDepartmentModal
                    open={isAddNewDepartmentOpen}
                    onClose={() => setIsAddNewDepartmentOpen(false)}
                    departments={allDepartments}
                >
                    <MDBTypography tag="div" className="display-6">
                        Add New Department
                    </MDBTypography>
                    <MDBTypography className="list-inline-item" tag="small">
                        Please fill in the required information in order to add a new department.
                    </MDBTypography>
                </AddNewDepartmentModal>
            )}
        </div>
    )
}
