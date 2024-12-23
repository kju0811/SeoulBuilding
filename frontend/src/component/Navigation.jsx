import React, { useState } from 'react';

export default function Navigation({ setSearchTerm }) {
    const [searchInput, setSearchInput] = useState('');

    const handleSearch = (event) => {
        event.preventDefault();
        setSearchTerm(searchInput); // 검색어를 상위 컴포넌트로 전달
    };

    return (
        <div className="navbar bg-base-100 w-full fixed top-0 left-0">
            <div className="flex-1">
                <a className="btn btn-ghost text-xl" href={"/"}>SeoulBuilding</a>
            </div>
            <div className="flex-none gap-2">
                <form onSubmit={handleSearch} className="form-control">
                    <input
                        type="text"
                        placeholder="Search"
                        className="input input-bordered w-24 md:w-auto"
                        value={searchInput}
                        onChange={(e) => setSearchInput(e.target.value)}
                    />
                </form>
            </div>
        </div>
    );
}