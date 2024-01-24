import React from 'react';
import {MDBCard, MDBCardBody, MDBCardHeader} from 'mdb-react-ui-kit';
import {Tab, TabList, TabPanel, Tabs} from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import NewsItem from './NewsItem';
import BestPracticeItem from './BestPracticeItem';
import '../scss/NewsBestPractices.scss';


function NewsBestPractices() {
    const list1 = [
        {
            id: 1,
            poster: 'Ali Kemal',
            date: '31/05/2023',
            link: 'https://docs.google.com/spreadsheets/d/17Vm1vaysz3nDmwN0sAGrELQwFn5d14u9TzIrxNbluiA/edit#gid=1486981300',
            title: '\t2022-2023 Spring Undergraduate Final Exam Schedule [UPDATED BBM234, TKD104]'
        },
        {
            id: 2,
            poster: 'Maruf',
            date: '21/05/2023',
            link: 'https://docs.google.com/spreadsheets/d/17Vm1vaysz3nDmwN0sAGrELQwFn5d14u9TzIrxNbluiA/edit#gid=1486981300',
            title: '18th Student Project Exhibition - 2023'
        },
        {
            id: 3,
            poster: 'Yasin',
            date: '12/04/2023',
            link: 'https://docs.google.com/spreadsheets/d/17Vm1vaysz3nDmwN0sAGrELQwFn5d14u9TzIrxNbluiA/edit#gid=1486981300',
            title: 'PhD Qualifying Exam Spring 2022-2023 Announcement'
        },
        {
            id: 4,
            poster: 'Cavid',
            date: '31/03/2023',
            link: 'https://docs.google.com/spreadsheets/d/17Vm1vaysz3nDmwN0sAGrELQwFn5d14u9TzIrxNbluiA/edit#gid=1486981300',
            title: 'HUBBM & AIN 2022-2023 Spring Semester Midterm Schedule Updated (BBM471)'
        },
        {
            id: 5,
            poster: 'Serkan',
            date: '28/02/2023',
            link: 'https://docs.google.com/spreadsheets/d/17Vm1vaysz3nDmwN0sAGrELQwFn5d14u9TzIrxNbluiA/edit#gid=1486981300',
            title: 'TÜBİTAK 2209-A University Students Research Projects Support Program Call'
        },
        {
            id: 6,
            poster: 'Fatih Terim',
            date: '12/01/2023',
            link: 'https://docs.google.com/spreadsheets/d/17Vm1vaysz3nDmwN0sAGrELQwFn5d14u9TzIrxNbluiA/edit#gid=1486981300',
            title: 'Online Lecture Links for Graduate Courses'
        },
    ];

    const list2 = [
        {
            id: 1,
            poster: 'Ali Kemal',
            date: '24/10/2022',
            link: 'https://www.bigocheatsheet.com/',
            title: 'Algorithm Cheat Sheet'
        },
        {
            id: 2,
            poster: 'Maruf',
            date: '26/12/2022',
            link: 'https://react.dev/',
            title: 'React.js Documentation'
        },
    ];

    return (
        <>
            <MDBCard className="m-2 card-1">
                <MDBCardHeader className='m-0 card-title'>
                    <h5 className='card-title'>News &amp; Best Practices</h5>
                </MDBCardHeader>
                <MDBCardBody className="m-0 p-0">
                    <Tabs className='m-1'>
                        <TabList>
                            <Tab>News</Tab>
                            <Tab>Best Practices</Tab>
                        </TabList>
                        <TabPanel>
                            <NewsItem items={list1}></NewsItem>
                        </TabPanel>
                        <TabPanel>
                            <BestPracticeItem items={list2}></BestPracticeItem>
                        </TabPanel>
                    </Tabs>
                </MDBCardBody>
            </MDBCard>
        </>
    );
}

export default NewsBestPractices;
