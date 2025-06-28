import React, { useState, useEffect } from 'react';
import { Chart } from 'primereact/chart';
import axios from 'axios';

function DoctorDashboard() {
    const [patientChartData, setPatientChartData] = useState({});
    const [patientChartOptions, setPatientChartOptions] = useState({});
    const [dateChartData, setDateChartData] = useState({});
    const [dateChartOptions, setDateChartOptions] = useState({});

    // PIE CHART - Appointments grouped by patient
    useEffect(() => {
        const fetchPatientChart = async () => {
            try {
                const res = await axios.get('http://localhost:8080/api/doctor/appointment/count-by-patient', {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                });

                const patients = res.data.patients.map(p => p.name);
                const counts = res.data.count;

                const documentStyle = getComputedStyle(document.documentElement);

                setPatientChartData({
                    labels: patients,
                    datasets: [
                        {
                            data: counts,
                            backgroundColor: [
                                documentStyle.getPropertyValue('--blue-500'),
                                documentStyle.getPropertyValue('--yellow-500'),
                                documentStyle.getPropertyValue('--green-500'),
                                documentStyle.getPropertyValue('--purple-500'),
                                documentStyle.getPropertyValue('--orange-500'),
                                documentStyle.getPropertyValue('--pink-500'),
                                documentStyle.getPropertyValue('--cyan-500'),
                            ],
                            hoverBackgroundColor: [
                                documentStyle.getPropertyValue('--blue-400'),
                                documentStyle.getPropertyValue('--yellow-400'),
                                documentStyle.getPropertyValue('--green-400'),
                                documentStyle.getPropertyValue('--purple-400'),
                                documentStyle.getPropertyValue('--orange-400'),
                                documentStyle.getPropertyValue('--pink-400'),
                                documentStyle.getPropertyValue('--cyan-400'),
                            ]
                        }
                    ]
                });

                setPatientChartOptions({
                    plugins: {
                        legend: {
                            labels: {
                                usePointStyle: true
                            }
                        }
                    }
                });
            } catch (error) {
                console.error("Error fetching patient appointment chart:", error);
            }
        };

        fetchPatientChart();
    }, []);

    // BAR CHART - Appointments grouped by date
    useEffect(() => {
        const fetchDateChart = async () => {
            try {
                const res = await axios.get('http://localhost:8080/api/doctor/appointment/count-by-date', {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                });

                const dates = res.data.dates;
                const counts = res.data.counts;

                const documentStyle = getComputedStyle(document.documentElement);

                setDateChartData({
                    labels: dates,
                    datasets: [
                        {
                            label: 'Appointments per Day',
                            data: counts,
                            backgroundColor: documentStyle.getPropertyValue('--blue-500'),
                            borderColor: documentStyle.getPropertyValue('--blue-700'),
                            borderWidth: 1
                        }
                    ]
                });

                setDateChartOptions({
                    responsive: true,
                    plugins: {
                        legend: {
                            display: false
                        },
                        title: {
                            display: true,
                            text: 'Daily Appointments'
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Count'
                            }
                        },
                        x: {
                            title: {
                                display: true,
                                text: 'Date'
                            }
                        }
                    }
                });
            } catch (error) {
                console.error("Error fetching date appointment chart:", error);
            }
        };

        fetchDateChart();
    }, []);

    return (
        <div className="dashboard-container p-4">
            <h2 className="text-center mb-4">Doctor Dashboard</h2>

            <div className="card mb-5">
                <h4 className="text-center mb-3">Appointments Grouped by Patient</h4>
                <Chart type="pie" data={patientChartData} options={patientChartOptions} className="w-full md:w-30rem mx-auto" />
            </div>

            <div className="card">
                <h4 className="text-center mb-3">Appointments Count per Day</h4>
                <Chart
                    type="bar"
                    data={dateChartData}
                    options={dateChartOptions}
                    style={{ width: '100%', maxWidth: '900px', height: '500px', margin: '0 auto' }}
                />
            </div>

        </div>
    );
}

export default DoctorDashboard;
