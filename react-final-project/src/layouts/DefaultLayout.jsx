import { Outlet } from "react-router-dom";
import { Navbar } from "../components/Navbar";
export function DefaultLayout() {
  return (
    <>
      <header>
        <Navbar></Navbar>
      </header>

      <main className="container my-5">
        <Outlet></Outlet>
      </main>
    </>
  );
}
