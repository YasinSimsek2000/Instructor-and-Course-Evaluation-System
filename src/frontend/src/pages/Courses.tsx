import React, {useEffect, useState} from 'react';
import '../scss/Home.scss';
import {Navigation} from '../components/Navigation';
import {LeftSide} from '../components/LeftSide';
import {MDBBtn, MDBCard, MDBCardBody, MDBCardFooter, MDBCardHeader, MDBIcon, MDBRadio, MDBRow} from "mdb-react-ui-kit";
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {Col, Row} from 'antd';
import AddCourseModal from "../components/AddCourseModal";
import axios from "axios";

interface ICourseData {
    id: number | undefined,
    name: string | undefined,
    departmentCode: string | undefined,
    courseCode: number | undefined,
    type: string | undefined,
    creditT: number | undefined,
    creditP: number | undefined,
    creditEcts: number | undefined,
    instructors: string[] | undefined,
}

interface ICourseCode {
    courseCode: number | undefined,
    departmentCode: string | undefined,
}

const deleteCourse = (courseId: number | undefined) => {
    const url = "http://localhost:8081/course/delete/".concat(courseId ? courseId.toString() : "")
    const token = localStorage.getItem('jwtToken')
    axios.defaults.headers['Authorization'] = token;
    axios.delete(url, {})
        . then(response => {
            if (response.status == 200) {

            }
        })
        .catch(error => {
        })
}

export const Courses = () => {
    const [isAddCourseOpen, setIsAddCourseOpen] = useState(false);
    const [isAddNewDepartmentOpen, setIsAddNewDepartmentOpen] = useState(false);
    const [newCourseName, setNewCourseName] = useState<string>();
    const [newDepartmentCode, setNewDepartmentCode] = useState<string>();
    const [newCourseCode, setNewCourseCode] = useState<number>();
    const [newCourseType, setNewCourseType] = useState<string>();
    const [newCourseCreditT, setNewCourseCreditT] = useState<number>();
    const [newCourseCreditP, setNewCourseCreditP] = useState<number>();
    const [newCourseCreditECTS, setNewCourseCreditECTS] = useState<number>();

    const [selectedCourse, setSelectedCourse] = useState<ICourseData | null>(null);
    const [showSurvey, setShowSurvey] = useState<boolean>(false);

    const [allCourses, setAllCourses] = useState<ICourseData[]>();

    const actionBodyTemplate = (rowData: ICourseData) => {
        return (
            <React.Fragment>
                <MDBBtn color='danger' tag='a' floating onClick={
                    () => deleteCourse(rowData.id)} className={"m-1"}
                ><MDBIcon fas icon='trash' /></MDBBtn>
                <MDBBtn color='primary' tag='a' floating onClick={
                    () => {
                        setSelectedCourse(rowData);
                        setShowSurvey(true);
                    }} className={"m-1"}
                ><MDBIcon fas icon='file' /></MDBBtn>
            </React.Fragment>
        );
    };

    useEffect(() => {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.get("http://localhost:8081/course/list", )
            . then(response => {
                if (response.status === 200) {
                    setAllCourses(response.data);

                }
            })
            .catch(error => {
                //
            });
    }, [allCourses]);

    return (
        <div>
            <Navigation />
            <Row>
                <Col className={'col-3'}>
                    <LeftSide />
                </Col>
                <Col className={"col-9"}>
                    {!showSurvey?
                        <MDBCard className={"m-2"}>
                            <MDBCardHeader className={"m-0 card-title"}>
                                <h5 className={"card-title"}>COURSES</h5>
                            </MDBCardHeader>
                            <MDBCardBody className={"m-1 p-1"}>
                                <DataTable
                                    value={allCourses}
                                    scrollable
                                    scrollHeight="400px"
                                    sortMode="multiple"
                                    dataKey="id"
                                    tableStyle={{ width: '100%'}}
                                >
                                    <Column field="name" header="Name" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                    <Column field="departmentCode" header="Dep. Code" sortable headerStyle={{backgroundColor: "#F8F9FA", width: "8%"}}></Column>
                                    <Column field="courseCode" header="Course Code" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                    <Column field="type" header="Type" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                    <Column field="creditT" header="Theoretical" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                    <Column field="creditP" header="Practical" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                    <Column field="creditEcts" header="ECTS Credit" sortable headerStyle={{backgroundColor: "#F8F9FA"}}></Column>
                                    <Column
                                        field="instructors"
                                        header="Instructors"
                                        sortable
                                        headerStyle={{ backgroundColor: "#F8F9FA" }}
                                        body={(rowData) => (
                                            <ul>
                                                {rowData.instructors &&
                                                    rowData.instructors.map((instructor: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | React.ReactFragment | React.ReactPortal | null | undefined, index: React.Key | null | undefined) => (
                                                        <li key={index}>{instructor}</li>
                                                    ))}
                                            </ul>
                                        )}
                                    />
                                    <Column body={actionBodyTemplate}></Column>
                                </DataTable>
                            </MDBCardBody>
                            <MDBCardFooter>
                                <MDBBtn className="m-1 w-25" onClick={() => setIsAddCourseOpen(true)}>Add New Course</MDBBtn>
                                <MDBBtn className="m-1 w-25">Assign an instructor to a course</MDBBtn>
                            </MDBCardFooter>
                        </MDBCard>

                        :

                        <MDBCard className={"m-2"}>
                            <MDBCardHeader>
                                <MDBRow>
                                    <div className={"col-4"}></div>
                                    <div className={"col-4 p-0"}>
                                        <h5 className={"card-title"}>{selectedCourse?.departmentCode}{selectedCourse?.courseCode} - SURVEY</h5>
                                    </div>
                                    <div className={"col-4 text-end"}>
                                        <MDBBtn className={"m-1 btn-danger"} onClick={() => setShowSurvey(false)}>Close</MDBBtn>
                                    </div>
                                </MDBRow>
                            </MDBCardHeader>
                            <MDBCardBody>
                                <h6>1. Do you like the instructor?</h6>
                                <MDBRadio name='inlineRadio' id='inlineRadio1' value='option1' label='1' inline />
                                <MDBRadio name='inlineRadio' id='inlineRadio2' value='option2' label='2' inline />
                                <MDBRadio name='inlineRadio' id='inlineRadio1' value='option1' label='3' inline />
                                <MDBRadio name='inlineRadio' id='inlineRadio2' value='option2' label='4' inline />
                                <MDBRadio name='inlineRadio' id='inlineRadio1' value='option1' label='5' inline />
                                <MDBRadio name='inlineRadio' id='inlineRadio2' value='option2' label='6' inline />
                                <MDBRadio name='inlineRadio' id='inlineRadio1' value='option1' label='7' inline />
                                <MDBRadio name='inlineRadio' id='inlineRadio2' value='option2' label='8' inline />
                                <MDBRadio name='inlineRadio' id='inlineRadio1' value='option1' label='9' inline />
                                <MDBRadio name='inlineRadio' id='inlineRadio2' value='option2' label='10' inline />
                            </MDBCardBody>
                            <MDBCardFooter>
                                <div className="text-end">
                                    <MDBBtn className={"m-1"}>Add Question</MDBBtn>
                                    <MDBBtn className={"m-1"}>Save and Continue Later</MDBBtn>
                                    <MDBBtn className={"m-1"}>Submit</MDBBtn>
                                </div>
                            </MDBCardFooter>
                        </MDBCard>
                    }
                </Col>
            </Row>
            {isAddCourseOpen && (
                <AddCourseModal
                    open={isAddCourseOpen}
                    onClose={() => setIsAddCourseOpen(false)}
                />
            )}
        </div>
    )
}
