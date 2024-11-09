import {useEffect,useState} from "react";
import {fetchUser} from "../api/UserAPI.jsx";

export default function Navigation() {
    const [user,setUser] = useState({});
    const ACCESS_TOKEN = localStorage.getItem('accesstoken');

    useEffect(() => {
        if (ACCESS_TOKEN) {
            fetchUser()
                .then((response) => {
                    setUser(response);
                }).catch((error) => {
                console.log(error);
            });
        }
    }, [ACCESS_TOKEN]);

    const handleLogout = async () => {
        localStorage.clear();
    };

   return (
       <div className="navbar bg-base-100 w-full fixed top-0 left-0 ">
           <div className="flex-1">
               <a className="btn btn-ghost text-xl " href={"/"}>SeoulBuilding</a>
           </div>
           <div className="flex-none gap-2">
               <div className="form-control">
                   <input type="text" placeholder="Search" className="input input-bordered w-24 md:w-auto"/>
               </div>
               <div className="dropdown dropdown-end">
                   <div tabIndex={0} role="button" className="btn btn-ghost btn-circle avatar">
                       <div className="w-10 rounded-full bg-secondary">
                       </div>
                   </div>
                   {ACCESS_TOKEN
                       ?
                       <ul
                           tabIndex={0}
                           className="menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow">
                           <li>
                               <a className="justify-between">
                                   Profile
                                   <span className="badge">New</span>
                               </a>
                           </li>
                           <li><a href="/" onClick={handleLogout}>Logout</a></li>
                       </ul>
                       :
                       <ul
                           tabIndex={0}
                           className="menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow">
                           <li>
                               <a className="justify-between" href="/Login">
                                   login
                               </a>
                           </li>
                           <li><a>signup</a></li>
                       </ul>
                   }
               </div>
           </div>
       </div>
   );
}
