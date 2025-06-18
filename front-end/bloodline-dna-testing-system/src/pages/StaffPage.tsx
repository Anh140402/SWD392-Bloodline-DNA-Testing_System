// The exported code uses Tailwind CSS. Install Tailwind CSS in your dev environment to ensure all styles work.

import React, { useState, useEffect } from "react";
import {
  Layout,
  Typography,
  Button,
  Input,
  Card,
  Row,
  Col,
  Table,
  Tag,
  Badge,
  Tabs,
  Modal,
  Space,
  Timeline,
  Statistic,
} from "antd";
import {
  SearchOutlined,
  CalendarOutlined,
  FileTextOutlined,
  CheckCircleOutlined,
  ClockCircleOutlined,
  ExclamationCircleOutlined,
  UserOutlined,
  BellOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
} from "@ant-design/icons";
import * as echarts from "echarts";

const { Header, Content, Footer, Sider } = Layout;
const { Title, Paragraph, Text } = Typography;
const { TabPane } = Tabs;

interface TestRecord {
  id: string;
  patientName: string;
  testType: string;
  status: string;
}

const App: React.FC = () => {
  const [collapsed, setCollapsed] = useState(false);
  const [activeTab, setActiveTab] = useState("1");

  useEffect(() => {
    const chartDom = document.getElementById("performanceChart");
    if (chartDom) {
      const myChart = echarts.init(chartDom);
      const option = {
        animation: false,
        tooltip: {
          trigger: "axis",
        },
        legend: {
          data: ["Tests Completed", "Average Processing Time"],
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
        },
        yAxis: [
          {
            type: "value",
            name: "Tests",
            position: "left",
          },
          {
            type: "value",
            name: "Minutes",
            position: "right",
          },
        ],
        series: [
          {
            name: "Tests Completed",
            type: "line",
            data: [12, 15, 10, 18, 14, 8, 5],
            yAxisIndex: 0,
            smooth: true,
            lineStyle: {
              width: 3,
              color: "#4096ff",
            },
            itemStyle: {
              color: "#4096ff",
            },
          },
          {
            name: "Average Processing Time",
            type: "line",
            data: [45, 42, 50, 38, 40, 52, 48],
            yAxisIndex: 1,
            smooth: true,
            lineStyle: {
              width: 3,
              color: "#ff7a45",
            },
            itemStyle: {
              color: "#ff7a45",
            },
          },
        ],
      };
      myChart.setOption(option);
    }
  }, []);

  const handleViewDetails = (record: TestRecord) => {
    Modal.info({
      title: "Test Request Details",
      content: (
        <div>
          <p>Patient ID: {record.id}</p>
          <p>Name: {record.patientName}</p>
          <p>Test Type: {record.testType}</p>
          <p>Status: {record.status}</p>
          <p>Requested: June 18, 2025 09:15 AM</p>
          <p>Priority: Normal</p>
          <p>Requesting Physician: Dr. Sarah Johnson</p>
          <p>Notes: Routine blood work for annual check-up</p>
        </div>
      ),
      width: 500,
    });
  };

  const handleProcessTest = (record: TestRecord) => {
    Modal.confirm({
      title: "Process Test Request",
      content: "Are you sure you want to process this test request?",
      onOk() {
        console.log("Processing test request:", record);
      },
    });
  };

  const TestRequestList: React.FC = () => {
    const columns = [
      {
        title: "ID",
        dataIndex: "id",
        key: "id",
      },
      {
        title: "Patient Name",
        dataIndex: "patientName",
        key: "patientName",
      },
      {
        title: "Test Type",
        dataIndex: "testType",
        key: "testType",
      },
      {
        title: "Status",
        dataIndex: "status",
        key: "status",
        render: (status: string) => {
          let color = "default";
          if (status === "Pending") color = "warning";
          if (status === "In Progress") color = "processing";
          if (status === "Completed") color = "success";
          return <Tag color={color}>{status}</Tag>;
        },
      },
      {
        title: "Action",
        key: "action",
        render: (_: any, record: TestRecord) => (
          <Space>
            <Button
              type="link"
              onClick={() => handleViewDetails(record)}
              className="!rounded-button whitespace-nowrap cursor-pointer"
            >
              View
            </Button>
            <Button
              type="link"
              onClick={() => handleProcessTest(record)}
              className="!rounded-button whitespace-nowrap cursor-pointer"
            >
              Process
            </Button>
          </Space>
        ),
      },
    ];

    const data: TestRecord[] = [
      {
        id: "T001",
        patientName: "John Doe",
        testType: "Blood Test",
        status: "Pending",
      },
      {
        id: "T002",
        patientName: "Jane Smith",
        testType: "COVID-19 PCR",
        status: "In Progress",
      },
      {
        id: "T003",
        patientName: "Mike Johnson",
        testType: "Urinalysis",
        status: "Completed",
      },
      {
        id: "T004",
        patientName: "Sarah Williams",
        testType: "Lipid Panel",
        status: "Pending",
      },
      {
        id: "T005",
        patientName: "Robert Chen",
        testType: "Liver Function",
        status: "In Progress",
      },
      {
        id: "T006",
        patientName: "Emily Davis",
        testType: "Complete Blood Count",
        status: "Pending",
      },
      {
        id: "T007",
        patientName: "David Wilson",
        testType: "Thyroid Panel",
        status: "Completed",
      },
    ];

    return (
      <Table columns={columns} dataSource={data} pagination={{ pageSize: 5 }} />
    );
  };

  return (
    <Layout className="min-h-screen">
      <Sider
        trigger={null}
        collapsible
        collapsed={collapsed}
        className="bg-white shadow-md"
      >
        <div className="p-4">
          <Title level={4} className="text-center text-blue-600">
            Lab Staff Portal
          </Title>
        </div>
        <div className="flex flex-col h-full">
          <div className="flex-1">
            <div
              className={`menu-item p-4 ${
                activeTab === "1" ? "bg-blue-50 text-blue-600" : "text-gray-700"
              } hover:bg-blue-50 hover:text-blue-600 cursor-pointer flex items-center`}
            >
              <FileTextOutlined className="mr-2" />{" "}
              <span className={collapsed ? "hidden" : ""}>Test Requests</span>
            </div>
            <div
              className={`menu-item p-4 ${
                activeTab === "2" ? "bg-blue-50 text-blue-600" : "text-gray-700"
              } hover:bg-blue-50 hover:text-blue-600 cursor-pointer flex items-center`}
            >
              <ClockCircleOutlined className="mr-2" />{" "}
              <span className={collapsed ? "hidden" : ""}>In Progress</span>
            </div>
            <div
              className={`menu-item p-4 ${
                activeTab === "3" ? "bg-blue-50 text-blue-600" : "text-gray-700"
              } hover:bg-blue-50 hover:text-blue-600 cursor-pointer flex items-center`}
            >
              <CheckCircleOutlined className="mr-2" />{" "}
              <span className={collapsed ? "hidden" : ""}>Completed</span>
            </div>
            <div
              className={`menu-item p-4 ${
                activeTab === "4" ? "bg-blue-50 text-blue-600" : "text-gray-700"
              } hover:bg-blue-50 hover:text-blue-600 cursor-pointer flex items-center`}
            >
              <CalendarOutlined className="mr-2" />{" "}
              <span className={collapsed ? "hidden" : ""}>Appointments</span>
            </div>
          </div>
        </div>
      </Sider>
      <Layout>
        <Header className="bg-white p-0 flex items-center justify-between px-4 shadow-sm h-16">
          <div className="flex items-center">
            <Button
              type="text"
              icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
              onClick={() => setCollapsed(!collapsed)}
              className="!rounded-button whitespace-nowrap cursor-pointer"
            />
            <Title level={4} className="m-0 ml-4">
              Laboratory Management System
            </Title>
          </div>
          <div className="flex items-center space-x-4">
            <Badge count={5} className="cursor-pointer">
              <BellOutlined style={{ fontSize: "20px" }} />
            </Badge>
            <div className="flex items-center space-x-2 cursor-pointer">
              <UserOutlined />
              <span>Dr. Alex Johnson</span>
            </div>
          </div>
        </Header>
        <Content className="bg-gray-50 p-6">
          <div className="mb-6">
            <Row gutter={16}>
              <Col span={6}>
                <Card className="shadow-sm">
                  <Statistic
                    title="Pending Tests"
                    value={15}
                    prefix={
                      <ExclamationCircleOutlined className="text-yellow-500" />
                    }
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card className="shadow-sm">
                  <Statistic
                    title="In Progress"
                    value={8}
                    prefix={<ClockCircleOutlined className="text-blue-500" />}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card className="shadow-sm">
                  <Statistic
                    title="Completed Today"
                    value={12}
                    prefix={<CheckCircleOutlined className="text-green-500" />}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card className="shadow-sm">
                  <Statistic
                    title="Total Appointments"
                    value={35}
                    prefix={<CalendarOutlined className="text-purple-500" />}
                  />
                </Card>
              </Col>
            </Row>
          </div>

          <Card className="mb-6 shadow-sm">
            <div className="flex justify-between items-center mb-4">
              <Title level={4} className="mb-0">
                Test Requests Management
              </Title>
              <Input.Search
                placeholder="Search requests..."
                style={{ width: 300 }}
                className="rounded-lg"
              />
            </div>
            <Tabs activeKey={activeTab} onChange={setActiveTab}>
              <TabPane tab="All Requests" key="1">
                <TestRequestList />
              </TabPane>
              <TabPane tab="Sample Collection" key="2">
                <TestRequestList />
              </TabPane>
              <TabPane tab="Processing" key="3">
                <TestRequestList />
              </TabPane>
              <TabPane tab="Results" key="4">
                <TestRequestList />
              </TabPane>
            </Tabs>
          </Card>

          <Row gutter={16}>
            <Col span={16}>
              <Card title="Weekly Performance" className="mb-6 shadow-sm">
                <div
                  id="performanceChart"
                  style={{ width: "100%", height: "300px" }}
                ></div>
              </Card>
            </Col>
            <Col span={8}>
              <Card title="Today's Schedule" className="mb-6 shadow-sm">
                <div className="space-y-4">
                  <div className="flex items-center justify-between p-2 bg-gray-50 rounded">
                    <div>
                      <Text strong>9:00 AM</Text>
                      <div>Blood Sample Collection</div>
                    </div>
                    <Tag color="processing">Upcoming</Tag>
                  </div>
                  <div className="flex items-center justify-between p-2 bg-gray-50 rounded">
                    <div>
                      <Text strong>10:30 AM</Text>
                      <div>PCR Test Processing</div>
                    </div>
                    <Tag color="warning">Pending</Tag>
                  </div>
                  <div className="flex items-center justify-between p-2 bg-gray-50 rounded">
                    <div>
                      <Text strong>2:00 PM</Text>
                      <div>Result Review Meeting</div>
                    </div>
                    <Tag color="default">Scheduled</Tag>
                  </div>
                </div>
              </Card>
            </Col>
          </Row>

          <Row gutter={16}>
            <Col span={12}>
              <Card title="Recent Activities" className="mb-6 shadow-sm">
                <Timeline>
                  <Timeline.Item color="green">
                    Test result uploaded for Patient #T003 - 11:45 AM
                  </Timeline.Item>
                  <Timeline.Item color="blue">
                    Sample collected from Patient #T005 - 10:30 AM
                  </Timeline.Item>
                  <Timeline.Item color="red">
                    Urgent test request from ER - Patient #T006 - 9:15 AM
                  </Timeline.Item>
                  <Timeline.Item>
                    New appointment scheduled for tomorrow - 8:45 AM
                  </Timeline.Item>
                  <Timeline.Item color="green">
                    Test result uploaded for Patient #T007 - Yesterday
                  </Timeline.Item>
                </Timeline>
              </Card>
            </Col>
            <Col span={12}>
              <Card title="Pending Urgent Tests" className="mb-6 shadow-sm">
                <Table
                  dataSource={[
                    {
                      id: "UT001",
                      patient: "Emma Thompson",
                      test: "Blood Glucose",
                      priority: "High",
                    },
                    {
                      id: "UT002",
                      patient: "James Wilson",
                      test: "Cardiac Enzymes",
                      priority: "Critical",
                    },
                    {
                      id: "UT003",
                      patient: "Olivia Martinez",
                      test: "Complete Blood Count",
                      priority: "High",
                    },
                  ]}
                  columns={[
                    { title: "ID", dataIndex: "id", key: "id" },
                    { title: "Patient", dataIndex: "patient", key: "patient" },
                    { title: "Test", dataIndex: "test", key: "test" },
                    {
                      title: "Priority",
                      dataIndex: "priority",
                      key: "priority",
                      render: (priority: string) => (
                        <Tag color={priority === "Critical" ? "red" : "orange"}>
                          {priority}
                        </Tag>
                      ),
                    },
                    {
                      title: "Action",
                      key: "action",
                      render: () => (
                        <Button
                          type="primary"
                          size="small"
                          className="!rounded-button whitespace-nowrap cursor-pointer"
                        >
                          Process Now
                        </Button>
                      ),
                    },
                  ]}
                  pagination={false}
                />
              </Card>
            </Col>
          </Row>
        </Content>
        <Footer className="bg-gray-100 text-gray-600 py-4 text-center">
          Medical Laboratory Staff Portal ©2025 Created by Healthcare System
        </Footer>
      </Layout>
    </Layout>
  );
};

export default App;
