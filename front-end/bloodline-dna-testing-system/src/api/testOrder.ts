// @ts-ignore

import Axios from "./Axios";
import type { AxiosResponse } from 'axios';
interface TestOrder {
  testOrderId: string;
  sampleId: string;
  accountId: string;
  serviceId: string;
  testOrderDate: string;
  preferredDate: string;
  orderType: string;
  status: string;
  isOnlyCase: boolean;
  note: string;
  appointmentId: string;
}

export const CreateTestOrder = async (orderData: TestOrder): Promise<any> => {
  const response: AxiosResponse = await Axios.post(
    "/Test%20Order/createTestOrder",
    orderData
  );
  return response.data;
};

export const GetAllTestOrders = async (): Promise<any> => {
  const response: AxiosResponse = await Axios.get("/Test%20Order/getAllTestOrders");
  return response.data;
};

export const DeleteTestOrder = async (id: string): Promise<any> => {
  const response: AxiosResponse = await Axios.delete(`/Test%20Order/deleteTestOrder/${id}`);
  return response.data;
};

export const GetAllUsers = async (): Promise<any> => {
  const response: AxiosResponse = await Axios.get("/accounts");
  return response.data;
};

export default {
  CreateTestOrder,
  GetAllTestOrders,
  DeleteTestOrder,
  GetAllUsers
};
